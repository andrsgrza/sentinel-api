package com.sentinel.video;

import com.sentinel.account.AccountEntity;
import com.sentinel.account.AccountRepository;
import com.sentinel.video.dto.CreateVideoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final AccountRepository accountRepository;

    public List<VideoEntity> findAll() {
        return videoRepository.findAll();
    }

    public VideoEntity createOrUpdate(CreateVideoRequest request) {
        Instant now = Instant.now();

        AccountEntity account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + request.getAccountId()));

        VideoEntity video = videoRepository
                .findByPlatformAndExternalVideoId(request.getPlatform(), request.getExternalVideoId())
                .orElseGet(() -> {
                    VideoEntity newVideo = VideoMapper.toEntity(request);
                    newVideo.setId(UUID.randomUUID());
                    newVideo.setCreatedAt(now);
                    return newVideo;
                });

        video.setAccount(account);
        video.setUrl(request.getUrl());
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());
        video.setPublishedAt(request.getPublishedAt());
        video.setDurationSeconds(request.getDurationSeconds());
        video.setViews(request.getViews());
        video.setLikes(request.getLikes());
        video.setComments(request.getComments());
        video.setShares(request.getShares());
        video.setSaves(request.getSaves());
        video.setHashtags(request.getHashtags());
        video.setSourceKeyword(request.getSourceKeyword());
        video.setUpdatedAt(now);

        return videoRepository.save(video);
    }
}