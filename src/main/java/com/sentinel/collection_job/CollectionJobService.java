package com.sentinel.collection_job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectionJobService {

    private final CollectionJobRepository collectionJobRepository;

    public List<CollectionJobEntity> findAll() {
        return collectionJobRepository.findAll();
    }

    public CollectionJobEntity createPendingJob(String platform, String keyword, Integer maxResults) {
        Instant now = Instant.now();

        CollectionJobEntity job = CollectionJobEntity.builder()
                .id(UUID.randomUUID())
                .platform(platform)
                .keyword(keyword)
                .status(CollectionJobStatus.PENDING)
                .maxResults(maxResults)
                .itemsFound(0)
                .createdAt(now)
                .updatedAt(now)
                .build();

        return collectionJobRepository.save(job);
    }

    public CollectionJobEntity markRunning(CollectionJobEntity job) {
        Instant now = Instant.now();

        job.setStatus(CollectionJobStatus.RUNNING);
        job.setStartedAt(now);
        job.setUpdatedAt(now);

        return collectionJobRepository.save(job);
    }

    public CollectionJobEntity markCompleted(CollectionJobEntity job, Integer itemsFound) {
        Instant now = Instant.now();

        job.setStatus(CollectionJobStatus.COMPLETED);
        job.setItemsFound(itemsFound);
        job.setFinishedAt(now);
        job.setUpdatedAt(now);

        return collectionJobRepository.save(job);
    }

    public CollectionJobEntity markFailed(CollectionJobEntity job, String errorMessage) {
        Instant now = Instant.now();

        job.setStatus(CollectionJobStatus.FAILED);
        job.setErrorMessage(errorMessage);
        job.setFinishedAt(now);
        job.setUpdatedAt(now);

        return collectionJobRepository.save(job);
    }
}