package com.sentinel.video.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class CreateVideoRequest {

    private UUID accountId;

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
}