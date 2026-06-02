package com.sentinel.collector.youtube;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.sentinel.collector.youtube.dto.YoutubeSearchResponse;
import com.sentinel.collector.youtube.dto.YoutubeVideoResponse;
import com.sentinel.collector.youtube.dto.YoutubeChannelResponse;

@RestController
@RequestMapping("/debug/youtube")
@RequiredArgsConstructor
public class YoutubeDebugController {

    private final YoutubeClient youtubeClient;

    @GetMapping("/search")
    public YoutubeSearchResponse search(
        @RequestParam String keyword,
        @RequestParam(defaultValue = "5") Integer maxResults) {
        return youtubeClient.searchVideos(keyword, maxResults);
    }

    @GetMapping("/videos")
    public YoutubeVideoResponse videos(@RequestParam String videoIds) {
        return youtubeClient.getVideos(videoIds);
    }

    @GetMapping("/channels")
    public YoutubeChannelResponse channels(@RequestParam String channelIds) {
        return youtubeClient.getChannels(channelIds);
    }
}