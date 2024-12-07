package com.microforum.gateway.filters;

import com.netflix.discovery.shared.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Configuration
public class FilterProcessor {

    private final Comparator<Pair<CustomFilter, Integer>> filterPriorityComparator =
            (entryA, entryB) -> Integer.compare(entryA.second(), entryB.second());
    private final PriorityQueue<Pair<CustomFilter, Integer>> filters = new PriorityQueue<>(filterPriorityComparator);
    Logger logger = LoggerFactory.getLogger(FilterProcessor.class);

    private final List<CustomFilter> getFilters() {
        PriorityQueue<Pair<CustomFilter, Integer>> filtersCopy = new PriorityQueue<>(filters);
        List<CustomFilter> filtersList = new ArrayList<>();
        while (!filtersCopy.isEmpty()) {
            Pair<CustomFilter, Integer> entry = filtersCopy.poll();
            filtersList.add(entry.first());
        }
        return filtersList;
    }

    public final void registerFilter(CustomFilter filter, @NonNull Integer order) {
        filters.add(new Pair<>(filter, order));
        logger.debug("Custom filter registered: {}", filter.filterName());
    }

    @Bean
    public GlobalFilter preFilter() {
        return (exchange, chain) -> {
            logger.debug("Running pre filters.");
            for (CustomFilter customFilter : getFilters()) {
                try {
                    exchange = customFilter.preFilter(exchange);
                } catch (Exception e) {
                    logger.error("Error executing {} of: {}", "PRE FILTER", customFilter.filterName(), e);
                }
            }
            return chain.filter(exchange);
        };
    }

    @Bean
    public GlobalFilter postFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        logger.debug("Running post filters.");
                        getFilters()
                                .forEach(customFilter -> {
                                    try {
                                        customFilter.postFilter(exchange);
                                    } catch (Exception e) {
                                        logger.error("Error executing {} of: {}", "POST FILTER", customFilter.filterName(), e);
                                    }
                                });
                    }));
        };
    }
}
