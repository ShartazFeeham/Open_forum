package com.microforum.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class TimeFilter extends CustomFilter {

    public static final String TIME_FILTER = "TimeFilter";
    public static final String REQUEST_ACCEPT_TIME = "X-RequestAcceptTime";
    public static final String REQUEST_DELIVERY_TIME = "X-RequestDeliveryTime";
    public static final String REQUEST_PROCESSING_TIME = "X-ProcessingTime";
    private final Logger logger = LoggerFactory.getLogger(TimeFilter.class);

    protected TimeFilter(FilterProcessor filterBucket) {
        super(filterBucket);
    }

    @Override
    protected String filterName() {
        return TIME_FILTER;
    }

    @Override
    protected int priorityIndex() {
        return 2;
    }

    @Override
    protected ServerWebExchange preFilter(ServerWebExchange exchange) {
        logger.debug("{} pre filter: adding request start time in request header.", filterName());
        return setRequestHeader(exchange, REQUEST_ACCEPT_TIME, String.valueOf(Instant.now().toEpochMilli()));
    }

    @Override
    protected ServerWebExchange postFilter(ServerWebExchange exchange) {
        logger.debug("{} post filter: adding request delivery time in response header.", filterName());
        exchange = setRequestHeader(exchange, REQUEST_DELIVERY_TIME, String.valueOf(Instant.now().toEpochMilli()));
        return attemptAddingProcessingTime(exchange);
    }

    @Override
    protected boolean isEnabled() {
        return true;
    }

    private ServerWebExchange attemptAddingProcessingTime(ServerWebExchange exchange) {
        Instant requestAcceptTime = readTimeFromHeader(exchange, REQUEST_ACCEPT_TIME);
        Instant requestDeliveryTime = readTimeFromHeader(exchange, REQUEST_DELIVERY_TIME);

        if (requestDeliveryTime != null && requestAcceptTime != null) {
            LocalDateTime requestAcceptDateTime = instantToLocalDateTime(requestAcceptTime);
            LocalDateTime requestDeliveryDateTime = instantToLocalDateTime(requestDeliveryTime);
            long processingTime = Math.abs(requestAcceptTime.toEpochMilli() - requestDeliveryTime.toEpochMilli());

            exchange.getResponse().getHeaders().add(REQUEST_ACCEPT_TIME, requestAcceptDateTime.toString());
            exchange.getResponse().getHeaders().add(REQUEST_DELIVERY_TIME, requestDeliveryDateTime.toString());
            exchange.getResponse().getHeaders().add(REQUEST_PROCESSING_TIME, String.valueOf(processingTime));

            logger.info("Request time: {}, Response time: {}, Processing time: {} ms",
                    requestAcceptTime, requestDeliveryTime, processingTime);
        }
        return exchange;
    }

    private Instant readTimeFromHeader(ServerWebExchange exchange, String timeParam) {
        List<String> times = exchange.getRequest().getHeaders().get(timeParam);
        if (CollectionUtils.isEmpty(times)) {
            return null;
        }
        return Instant.ofEpochMilli(Long.parseLong(times.getFirst()));
    }

    private LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
