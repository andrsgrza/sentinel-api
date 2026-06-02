package com.sentinel.video;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<VideoEntity, UUID> {

    Optional<VideoEntity> findByPlatformAndExternalVideoId(
            String platform,
            String externalVideoId
    );
}