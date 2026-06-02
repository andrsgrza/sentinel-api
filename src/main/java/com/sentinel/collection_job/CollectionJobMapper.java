package com.sentinel.collection_job;

import com.sentinel.collection_job.dto.CollectionJobResponse;

public class CollectionJobMapper {

    private CollectionJobMapper() {
    }

    public static CollectionJobResponse toResponse(CollectionJobEntity entity) {
        return CollectionJobResponse.builder()
                .id(entity.getId())
                .platform(entity.getPlatform())
                .keyword(entity.getKeyword())
                .status(entity.getStatus())
                .maxResults(entity.getMaxResults())
                .itemsFound(entity.getItemsFound())
                .errorMessage(entity.getErrorMessage())
                .startedAt(entity.getStartedAt())
                .finishedAt(entity.getFinishedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}