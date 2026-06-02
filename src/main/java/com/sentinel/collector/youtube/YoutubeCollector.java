package com.sentinel.collector.youtube;

import com.sentinel.collector.CollectionConfig;
import com.sentinel.collector.CollectionResult;
import com.sentinel.collector.ContentCollector;
import com.sentinel.collector.model.CollectedAccount;
import com.sentinel.collector.model.CollectedVideo;
import com.sentinel.collector.youtube.dto.YoutubeChannelResponse;
import com.sentinel.collector.youtube.dto.YoutubeSearchResponse;
import com.sentinel.collector.youtube.dto.YoutubeVideoResponse;
import com.sentinel.collector.youtube.mapper.YoutubeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class YoutubeCollector implements ContentCollector {

    private static final String PLATFORM = "youtube";

    private final YoutubeClient youtubeClient;
    private final YoutubeMapper youtubeMapper;

    @Override
    public String getPlatform() {
        return PLATFORM;
    }

    @Override
    public CollectionResult collect(CollectionConfig config) {
        YoutubeSearchResponse searchResponse = youtubeClient.searchVideos(
                config.getKeyword(),
                config.getMaxResults()
        );

        String videoIds = searchResponse.getItems()
                .stream()
                .map(item -> item.getId().getVideoId())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.joining(","));

        String channelIds = searchResponse.getItems()
                .stream()
                .map(item -> item.getSnippet().getChannelId())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.joining(","));

        YoutubeVideoResponse videoResponse = youtubeClient.getVideos(videoIds);
        YoutubeChannelResponse channelResponse = youtubeClient.getChannels(channelIds);

        List<CollectedVideo> videos = videoResponse.getItems()
                .stream()
                .map(youtubeMapper::toCollectedVideo)
                .peek(video -> video.setSourceKeyword(config.getKeyword()))
                .toList();

        List<CollectedAccount> accounts = channelResponse.getItems()
                .stream()
                .map(youtubeMapper::toCollectedAccount)
                .toList();

        return CollectionResult.builder()
                .itemsFound(videos.size())
                .accounts(accounts)
                .videos(videos)
                .build();
    }
}