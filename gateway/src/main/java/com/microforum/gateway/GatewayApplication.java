package com.microforum.gateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

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
								.circuitBreaker(config -> config.setName("postsCircuitBreaker")
										.setFallbackUri("forward:/fallback/posts"))
						).uri("lb://posts"))
				// Add more route for other services
				.build();
	}
}
