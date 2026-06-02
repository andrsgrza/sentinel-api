package com.sentinel.collector.youtube;

import com.sentinel.collector.CollectionConfig;
import com.sentinel.collector.CollectionResult;
import com.sentinel.collector.ContentCollector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockYouTubeCollector implements ContentCollector {

    @Override
    public String getPlatform() {
        return "youtube";
    }

    @Override
    public CollectionResult collect(CollectionConfig config) {
        return CollectionResult.builder()
                .itemsFound(config.getMaxResults())
                .accounts(List.of())
                .videos(List.of())
                .build();
    }
}