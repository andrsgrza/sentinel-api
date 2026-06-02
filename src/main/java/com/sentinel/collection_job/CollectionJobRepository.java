package com.sentinel.collection_job;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CollectionJobRepository extends JpaRepository<CollectionJobEntity, UUID> {
}