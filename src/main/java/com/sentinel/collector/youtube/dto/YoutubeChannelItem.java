package com.sentinel.collector.youtube.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class YoutubeChannelItem {

    private String kind;
    private String etag;
    private String id;

    private Snippet snippet;

    private Statistics statistics;

    @Getter
    @Setter
    public static class Snippet {
        private String title;
        private String description;
        private String customUrl;
        private Instant publishedAt;
        private Map<String, YoutubeSearchItem.Thumbnail> thumbnails;
        private String defaultLanguage;
        private Localized localized;
        private String country;
    }

    @Getter
    @Setter
    public static class Localized {
        private String title;
        private String description;
    }

    @Getter
    @Setter
    public static class Statistics {
        private String viewCount;
        private String subscriberCount;
        private Boolean hiddenSubscriberCount;
        private String videoCount;
    }
}