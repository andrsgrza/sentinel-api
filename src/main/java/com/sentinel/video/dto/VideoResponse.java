package com.sentinel.video.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class VideoResponse {

    private UUID id;

    private UUID accountId;
    private String accountHandle;

    private String platform;
    private String externalVideoId;
    private String url;

    private String title;
    private String description;
    private Instant publishedAt;
    private Integer durationSeconds;

    private Long views;
    private Long likes;
    private Long comments;
    private Long shares;
    private Long saves;

    private String hashtags;
    private String sourceKeyword;

    private Instant createdAt;
    private Instant updatedAt;
}