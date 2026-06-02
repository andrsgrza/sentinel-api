package com.sentinel.collector.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class CollectedVideo {

    private String platform;

    private String externalVideoId;

    private String externalAccountId;

    private String url;

    private String title;

    private String description;

    private Instant publishedAt;

    private Integer durationSeconds;

    private Long views;

    private Long likes;

    private Long comments;
}