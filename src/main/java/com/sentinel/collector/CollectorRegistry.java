package com.sentinel.collector;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CollectorRegistry {

    private final Map<String, ContentCollector> collectorsByPlatform;

    public CollectorRegistry(List<ContentCollector> collectors) {
        this.collectorsByPlatform = collectors.stream()
                .collect(Collectors.toMap(
                        collector -> collector.getPlatform().toLowerCase(),
                        Function.identity()
                ));
    }

    public ContentCollector getCollector(String platform) {
        ContentCollector collector = collectorsByPlatform.get(platform.toLowerCase());

        if (collector == null) {
            throw new IllegalArgumentException("No collector found for platform: " + platform);
        }

        return collector;
    }
}