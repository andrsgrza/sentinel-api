package com.sentinel.collector.youtube.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YoutubeVideoResponse {

    private String kind;
    private String etag;

    private List<YoutubeVideoItem> items;

    private PageInfo pageInfo;

    @Getter
    @Setter
    public static class PageInfo {
        private Integer totalResults;
        private Integer resultsPerPage;
    }
}