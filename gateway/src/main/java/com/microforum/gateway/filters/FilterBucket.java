package com.microforum.gateway.filters;

import com.netflix.discovery.shared.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.util.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class FilterBucket {

    private final Logger logger = LoggerFactory.getLogger(FilterBucket.class);
    private final Comparator<Pair<CustomFilter, Integer>> filterPriorityComparator =
            (entryA, entryB) -> Integer.compare(entryA.second(), entryB.second());
    private final PriorityQueue<Pair<CustomFilter, Integer>> filters = new PriorityQueue<>(filterPriorityComparator);

    public final List<CustomFilter> getFilters() {
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
}
