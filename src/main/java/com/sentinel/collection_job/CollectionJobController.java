package com.sentinel.collection_job;

import com.sentinel.collection_job.dto.CollectionJobResponse;
import com.sentinel.collection_job.dto.CreateCollectionJobRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collection-jobs")
@RequiredArgsConstructor
public class CollectionJobController {

    private final CollectionJobService collectionJobService;

    @GetMapping
    public List<CollectionJobResponse> findAll() {
        return collectionJobService.findAll()
                .stream()
                .map(CollectionJobMapper::toResponse)
                .toList();
    }

    @PostMapping
    public CollectionJobResponse create(@RequestBody CreateCollectionJobRequest request) {
        CollectionJobEntity job = collectionJobService.createPendingJob(
                request.getPlatform(),
                request.getKeyword(),
                request.getMaxResults()
        );

        return CollectionJobMapper.toResponse(job);
    }
}