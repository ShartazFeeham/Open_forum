package com.microforum.gateway.filters;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.UUID;

@Configuration
public class CorrelationFilter extends CustomFilter {

    public static final String CORRELATION_FILTER = "CorrelationFilter";
    private final Logger logger = LoggerFactory.getLogger(CorrelationFilter.class);
    public static final String CORRELATION_ID_KEY = "X-Correlation-ID";
    public static final int CORRELATION_ID_LENGTH = 8;

    protected CorrelationFilter(FilterProcessor filterBucket) {
        super(filterBucket);
    }

    @Override
    protected String filterName() {
        return CORRELATION_FILTER;
    }

    @Override
    protected int priorityIndex() {
        return 1;
    }

    @Override
    protected ServerWebExchange preFilter(ServerWebExchange exchange) {
        logger.debug("CorrelationFilter preFilter: adding correlation id in request header.");
        String correlationID = getCorrelationId(exchange.getRequest().getHeaders());

        if (StringUtils.isEmpty(correlationID)) {
            correlationID = generateCorrelationId();
            exchange = setRequestHeader(exchange, CORRELATION_ID_KEY, correlationID);
            logger.debug("New " + CORRELATION_ID_KEY + " generated: {}", correlationID);
        } else {
            logger.debug(CORRELATION_ID_KEY + " found in RequestTraceFilter: {}", correlationID);
        }
        return exchange;
    }

    @Override
    protected ServerWebExchange postFilter(ServerWebExchange exchange) {
        logger.debug("CorrelationFilter postFilter: adding correlation id in response header.");
        String correlationId = getCorrelationId(exchange.getRequest().getHeaders());
        exchange.getResponse().getHeaders().add(CORRELATION_ID_KEY, correlationId);
        logger.debug("Updated the correlation id to response headers: {}", correlationId);
        return exchange;
    }

    private String getCorrelationId(HttpHeaders requestHeaders) {
        if (!CollectionUtils.isEmpty(requestHeaders.get(CORRELATION_ID_KEY))) {
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID_KEY);
            if (!CollectionUtils.isEmpty(requestHeaderList)) {
                return requestHeaderList.getFirst();
            }
        }
        return null;
    }

    private String generateCorrelationId() {
        String randomID = UUID.randomUUID().toString();
        StringBuilder cleanIdBuilder = new StringBuilder();
        int index = 0;
        while (cleanIdBuilder.length() < CORRELATION_ID_LENGTH) {
            if (Character.isAlphabetic(randomID.charAt(index)) || Character.isDigit(randomID.charAt(index))) {
                cleanIdBuilder.append(randomID.charAt(index));
            }
            index++;
        }
        return cleanIdBuilder.toString().toUpperCase();
    }
}
