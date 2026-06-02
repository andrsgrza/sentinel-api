package com.sentinel.collector.youtube.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class YoutubeSearchItem {

    private String kind;
    private String etag;

    private Id id;

    private Snippet snippet;

    @Getter
    @Setter
    public static class Id {
        private String kind;
        private String videoId;
    }

    @Getter
    @Setter
    public static class Snippet {
        private Instant publishedAt;
        private String channelId;
        private String title;
        private String description;
        private Map<String, Thumbnail> thumbnails;
        private String channelTitle;
        private String liveBroadcastContent;
        private Instant publishTime;
    }

    @Getter
    @Setter
    public static class Thumbnail {
        private String url;
        private Integer width;
        private Integer height;
    }
}