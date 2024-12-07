package com.microforum.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Value("${constants.retry.maxAttempts}")
	private int maxAttempts;

	@Value("${constants.retry.backoff.initial}")
	private long initialBackoff;

	@Value("${constants.retry.backoff.max}")
	private long maxBackoff;

	@Value("${constants.retry.backoff.multiplier}")
	private int backoffMultiplier;

	@Value("${constants.retry.backoff.multiplyPrevious}")
	private boolean multiplyPrevious;

	@Bean
	public RouteLocator microForumCustomRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/micro-forum/posts/**")
						.filters(filter -> filter
								.rewritePath("/micro-forum/posts/(?<segment>.*)","/${segment}")
								// Added to understand customization of request/response from here
								// .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								// Removed after adding time filter as it already handles similar works
								// .removeResponseHeader("X-Response-Time")
								.circuitBreaker(config -> config
										.setName("postsCircuitBreaker")
										.setFallbackUri("forward:/fallback/posts")
								)
								.retry(config -> config
										.setRetries(maxAttempts) // Number of retries to attempt (one initial hit + 3 retries)
										.setMethods(HttpMethod.GET) // Only idempotent methods are okay for retry
										.setMethods(HttpMethod.DELETE)
										.setBackoff(Duration.ofMillis(initialBackoff), // First attempt will be after 100ms
												// The maximum limit to wait for. Math.min((previousDuration * factor), maxBackOff)
												Duration.ofMillis(maxBackoff),
												backoffMultiplier, // Multiply factor
												// Each next duration will be calculated by multiplying previous duration
												// So, true will result in exponential and false will be multiplicative
												multiplyPrevious)
								)
						).uri("lb://posts"))
				// Add more route for other services
				.build();
	}
}
