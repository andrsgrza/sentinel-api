package com.sentinel.collector;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class CollectionConfig {

    private String platform;

    private String keyword;

    private Integer maxResults;

    private String order;

    private String regionCode;

    private String language;

    private Instant publishedAfter;

    private Instant publishedBefore;

    private String videoDuration;

    private String safeSearch;

    @Builder.Default
    private Boolean deduplicate = true;
}