package com.sentinel.collector.youtube;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.sentinel.collector.youtube.dto.YoutubeSearchResponse;
import com.sentinel.collector.youtube.dto.YoutubeVideoResponse;
import com.sentinel.collector.youtube.dto.YoutubeChannelResponse;

@Component
@RequiredArgsConstructor
public class YoutubeClient {

    private static final String YOUTUBE_API_BASE_URL = "https://www.googleapis.com/youtube/v3";

    private final RestClient restClient = RestClient.create();

    @Value("${youtube.api-key}")
    private String apiKey;

    public YoutubeSearchResponse searchVideos(String keyword, Integer maxResults) {
        String url = UriComponentsBuilder
                .fromUriString(YOUTUBE_API_BASE_URL + "/search")
                .queryParam("part", "snippet")
                .queryParam("q", keyword)
                .queryParam("type", "video")
                .queryParam("maxResults", maxResults)
                .queryParam("key", apiKey)
                .toUriString();

        return restClient.get()
            .uri(url)
            .retrieve()
            .body(YoutubeSearchResponse.class);
    }

    public YoutubeVideoResponse getVideos(String videoIds) {
        String url = UriComponentsBuilder
                .fromUriString(YOUTUBE_API_BASE_URL + "/videos")
                .queryParam("part", "snippet,statistics,contentDetails")
                .queryParam("id", videoIds)
                .queryParam("key", apiKey)
                .toUriString();

        return restClient.get()
            .uri(url)
            .retrieve()
            .body(YoutubeVideoResponse.class);
    }

    public YoutubeChannelResponse getChannels(String channelIds) {
        String url = UriComponentsBuilder
                .fromUriString(YOUTUBE_API_BASE_URL + "/channels")
                .queryParam("part", "snippet,statistics")
                .queryParam("id", channelIds)
                .queryParam("key", apiKey)
                .toUriString();

        return restClient.get()
            .uri(url)
            .retrieve()
            .body(YoutubeChannelResponse.class);
    }
}