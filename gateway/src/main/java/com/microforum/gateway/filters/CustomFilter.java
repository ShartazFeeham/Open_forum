package com.microforum.gateway.filters;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

@Service
public abstract class CustomFilter {

    private final FilterProcessor filterBucket;
    protected CustomFilter(FilterProcessor filterBucket) {
        this.filterBucket = filterBucket;
    }

    /**
     * Name of the filter
     * @return returns the filter name
     */
    protected abstract String filterName();

    /**
     * Define how early the filter will be executed, lower index executes first.
     * @return priority index value
     */
    protected abstract int priorityIndex();

    /**
     * Pre-filter logic that will be executed before processing the request
     *
     * @param exchange {@link ServerWebExchange}
     * @return {@link ServerWebExchange}
     */
    protected abstract ServerWebExchange preFilter(ServerWebExchange exchange);

    /**
     * Post-filter logic that will be executed after processing the request
     * @param exchange {@link ServerWebExchange}
     * @return {@link ServerWebExchange}
     */
    protected abstract ServerWebExchange postFilter(ServerWebExchange exchange);

    @PostConstruct
    public final void registerSelf() {
        filterBucket.registerFilter(this, priorityIndex());
    }

    protected ServerWebExchange setRequestHeader(ServerWebExchange exchange, String headerParamName, String value) {
        return exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .header(headerParamName, value)
                        .build()
                ).build();
    }
}
