package com.microforum.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class FilterDriver {

    Logger logger = LoggerFactory.getLogger(FilterDriver.class);
    private final FilterBucket filterBucket;

    public FilterDriver(FilterBucket filterBucket) {
        this.filterBucket = filterBucket;
    }

    @Bean
    public GlobalFilter preFilter() {
        return (exchange, chain) -> {
            logger.debug("Inside pre filter");
            filterBucket.getFilters()
                    .forEach(customFilter -> tryExecutingFilter(
                            () -> customFilter.preFilter(exchange), customFilter.filterName(), "Pre Filter")
                    );
            return chain.filter(exchange);
        };
    }

    @Bean
    public GlobalFilter postFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        logger.debug("Inside post filter");
                        filterBucket.getFilters()
                                .forEach(customFilter -> tryExecutingFilter(
                                        () -> customFilter.postFilter(exchange), customFilter.filterName(), "Post Filter")
                                );
                    }));
        };
    }

    private void tryExecutingFilter(Runnable runnableFilter, String filterName, String filterType) {
        try {
            runnableFilter.run();
        } catch (Exception e) {
            logger.error("Error executing {} of filter: {}", filterType.toUpperCase(), filterName, e);
        }
    }
}
