package com.sentinel.collection_job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentinel.collector.CollectionConfig;
import com.sentinel.collector.CollectionResult;
import com.sentinel.collector.CollectorRegistry;
import com.sentinel.collector.ContentCollector;
import com.sentinel.collector.CollectionPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectionJobService {

    private final CollectionJobRepository collectionJobRepository;
    private final CollectorRegistry collectorRegistry;
    private final CollectionPersistenceService collectionPersistenceService;

    private final ObjectMapper objectMapper = new ObjectMapper()
        .findAndRegisterModules();

    public List<CollectionJobEntity> findAll() {
        return collectionJobRepository.findAll();
    }

    public CollectionJobEntity runJob(String platform, String keyword, Integer maxResults) {
        CollectionConfig config = CollectionConfig.builder()
                .platform(platform)
                .keyword(keyword)
                .maxResults(maxResults)
                .deduplicate(true)
                .build();

        CollectionJobEntity job = createPendingJob(config);

        try {
            job = markRunning(job);

            ContentCollector collector = collectorRegistry.getCollector(platform);

            CollectionResult result = collector.collect(config);

            collectionPersistenceService.persist(result);

            return markCompleted(job, result);
        } catch (Exception error) {
            return markFailed(job, error.getMessage());
        }
    }

    private CollectionJobEntity createPendingJob(CollectionConfig config) {
        Instant now = Instant.now();

        CollectionJobEntity job = CollectionJobEntity.builder()
                .id(UUID.randomUUID())
                .platform(config.getPlatform())
                .keyword(config.getKeyword())
                .status(CollectionJobStatus.PENDING)
                .maxResults(config.getMaxResults())
                .configJson(toJson(config))
                .itemsFound(0)
                .createdAt(now)
                .updatedAt(now)
                .build();

        return collectionJobRepository.save(job);
    }

    private CollectionJobEntity markRunning(CollectionJobEntity job) {
        Instant now = Instant.now();

        job.setStatus(CollectionJobStatus.RUNNING);
        job.setStartedAt(now);
        job.setUpdatedAt(now);

        return collectionJobRepository.save(job);
    }

    private CollectionJobEntity markCompleted(CollectionJobEntity job, CollectionResult result) {
        Instant now = Instant.now();

        job.setStatus(CollectionJobStatus.COMPLETED);
        job.setItemsFound(result.getItemsFound());
        job.setResultJson(toJson(result));
        job.setFinishedAt(now);
        job.setUpdatedAt(now);

        return collectionJobRepository.save(job);
    }

    private CollectionJobEntity markFailed(CollectionJobEntity job, String errorMessage) {
        Instant now = Instant.now();

        job.setStatus(CollectionJobStatus.FAILED);
        job.setErrorMessage(errorMessage);
        job.setFinishedAt(now);
        job.setUpdatedAt(now);

        return collectionJobRepository.save(job);
    }

    private String toJson(CollectionConfig config) {
        try {
            return objectMapper.writeValueAsString(config);
        } catch (JsonProcessingException error) {
            throw new IllegalArgumentException("Failed to serialize collection config", error);
        }
    }

    private String toJson(CollectionResult result) {
        try {
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException error) {
            throw new IllegalArgumentException(
                "Failed to serialize collection result: " + error.getOriginalMessage(),
                error
            );
        }
    }
}