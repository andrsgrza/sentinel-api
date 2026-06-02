package com.sentinel.collector.youtube;

import com.sentinel.collector.CollectionRequest;
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
    public CollectionResult collect(CollectionRequest request) {

        return CollectionResult.builder()
                .itemsFound(request.getMaxResults())
                .accounts(List.of())
                .videos(List.of())
                .build();
    }
}