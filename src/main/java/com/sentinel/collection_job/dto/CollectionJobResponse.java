package com.sentinel.collection_job.dto;

import com.sentinel.collection_job.CollectionJobStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class CollectionJobResponse {

    private UUID id;

    private String platform;
    private String keyword;
    private CollectionJobStatus status;

    private Integer maxResults;
    private Integer itemsFound;

    private String errorMessage;

    private Instant startedAt;
    private Instant finishedAt;

    private Instant createdAt;
    private Instant updatedAt;
}