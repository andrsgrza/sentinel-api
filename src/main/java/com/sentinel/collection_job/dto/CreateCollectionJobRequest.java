package com.sentinel.collection_job.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCollectionJobRequest {

    private String platform;
    private String keyword;
    private Integer maxResults;
}