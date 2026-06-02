package com.sentinel.collector.youtube.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class YoutubeVideoItem {

    private String kind;
    private String etag;
    private String id;

    private Snippet snippet;

    private ContentDetails contentDetails;

    private Statistics statistics;

    @Getter
    @Setter
    public static class Snippet {
        private Instant publishedAt;
        private String channelId;
        private String title;
        private String description;
        private Map<String, YoutubeSearchItem.Thumbnail> thumbnails;
        private String channelTitle;
        private List<String> tags;
        private String categoryId;
        private String liveBroadcastContent;
        private String defaultLanguage;
        private String defaultAudioLanguage;
    }

    @Getter
    @Setter
    public static class ContentDetails {
        private String duration;
        private String dimension;
        private String definition;
        private String caption;
        private Boolean licensedContent;
        private String projection;
    }

    @Getter
    @Setter
    public static class Statistics {
        private String viewCount;
        private String likeCount;
        private String favoriteCount;
        private String commentCount;
    }
}