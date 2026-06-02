package com.sentinel.video;

import com.sentinel.video.dto.CreateVideoRequest;
import com.sentinel.video.dto.VideoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public List<VideoResponse> findAll() {
        return videoService.findAll()
                .stream()
                .map(VideoMapper::toResponse)
                .toList();
    }

    @PostMapping
    public VideoResponse create(@RequestBody CreateVideoRequest request) {
        VideoEntity video = videoService.createOrUpdate(request);
        return VideoMapper.toResponse(video);
    }
}