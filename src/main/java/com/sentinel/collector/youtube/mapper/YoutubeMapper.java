package com.sentinel.collector.youtube.mapper;

import com.sentinel.collector.model.CollectedAccount;
import com.sentinel.collector.model.CollectedVideo;
import com.sentinel.collector.youtube.dto.YoutubeChannelItem;
import com.sentinel.collector.youtube.dto.YoutubeVideoItem;
import org.springframework.stereotype.Component;

@Component
public class YoutubeMapper {

    public CollectedAccount toCollectedAccount(
            YoutubeChannelItem channel
    ) {

        String customUrl = channel.getSnippet().getCustomUrl();

        return CollectedAccount.builder()
                .platform("youtube")
                .externalAccountId(channel.getId())
                .handle(customUrl)
                .displayName(channel.getSnippet().getTitle())
                .profileUrl(
                        customUrl == null
                                ? null
                                : "https://www.youtube.com/" + customUrl
                )
                .followers(
                        channel.getStatistics().getSubscriberCount() == null
                                ? null
                                : Long.valueOf(
                                        channel.getStatistics().getSubscriberCount()
                                )
                )
                .build();
    }

    public CollectedVideo toCollectedVideo(
            YoutubeVideoItem video
    ) {

        return CollectedVideo.builder()
                .platform("youtube")
                .externalVideoId(video.getId())
                .externalAccountId(video.getSnippet().getChannelId())
                .url("https://www.youtube.com/watch?v=" + video.getId())
                .title(video.getSnippet().getTitle())
                .description(video.getSnippet().getDescription())
                .publishedAt(video.getSnippet().getPublishedAt())
                .views(
                        video.getStatistics().getViewCount() == null
                                ? null
                                : Long.valueOf(video.getStatistics().getViewCount())
                )
                .likes(
                        video.getStatistics().getLikeCount() == null
                                ? null
                                : Long.valueOf(video.getStatistics().getLikeCount())
                )
                .comments(
                        video.getStatistics().getCommentCount() == null
                                ? null
                                : Long.valueOf(video.getStatistics().getCommentCount())
                )
                .build();
    }
}