package com.sentinel.collector;

import com.sentinel.collector.model.CollectedAccount;
import com.sentinel.collector.model.CollectedVideo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CollectionResult {

    private Integer itemsFound;

    private List<CollectedAccount> accounts;

    private List<CollectedVideo> videos;
}