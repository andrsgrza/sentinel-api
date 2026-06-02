package com.sentinel.video;

import com.sentinel.video.dto.CreateVideoRequest;
import com.sentinel.video.dto.VideoResponse;

public class VideoMapper {

    private VideoMapper() {
    }

    public static VideoEntity toEntity(CreateVideoRequest request) {
        return VideoEntity.builder()
                .platform(request.getPlatform())
                .externalVideoId(request.getExternalVideoId())
                .url(request.getUrl())
                .title(request.getTitle())
                .description(request.getDescription())
                .publishedAt(request.getPublishedAt())
                .durationSeconds(request.getDurationSeconds())
                .views(request.getViews())
                .likes(request.getLikes())
                .comments(request.getComments())
                .shares(request.getShares())
                .saves(request.getSaves())
                .hashtags(request.getHashtags())
                .sourceKeyword(request.getSourceKeyword())
                .build();
    }

    public static VideoResponse toResponse(VideoEntity entity) {
        return VideoResponse.builder()
                .id(entity.getId())
                .accountId(entity.getAccount().getId())
                .accountHandle(entity.getAccount().getHandle())
                .platform(entity.getPlatform())
                .externalVideoId(entity.getExternalVideoId())
                .url(entity.getUrl())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .publishedAt(entity.getPublishedAt())
                .durationSeconds(entity.getDurationSeconds())
                .views(entity.getViews())
                .likes(entity.getLikes())
                .comments(entity.getComments())
                .shares(entity.getShares())
                .saves(entity.getSaves())
                .hashtags(entity.getHashtags())
                .sourceKeyword(entity.getSourceKeyword())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}