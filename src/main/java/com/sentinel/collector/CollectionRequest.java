package com.sentinel.collector;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CollectionRequest {

    private String keyword;
    private Integer maxResults;
}