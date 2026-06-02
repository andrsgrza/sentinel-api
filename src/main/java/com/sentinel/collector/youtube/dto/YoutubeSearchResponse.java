package com.sentinel.collector.youtube.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YoutubeSearchResponse {

    private String kind;
    private String etag;
    private String nextPageToken;
    private String regionCode;

    private PageInfo pageInfo;

    private List<YoutubeSearchItem> items;

    @Getter
    @Setter
    public static class PageInfo {
        private Integer totalResults;
        private Integer resultsPerPage;
    }
}