package com.sentinel.collector.youtube.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YoutubeChannelResponse {

    private String kind;
    private String etag;

    private PageInfo pageInfo;

    private List<YoutubeChannelItem> items;

    @Getter
    @Setter
    public static class PageInfo {
        private Integer totalResults;
        private Integer resultsPerPage;
    }
}