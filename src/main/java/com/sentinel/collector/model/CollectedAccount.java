package com.sentinel.collector.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CollectedAccount {

    private String platform;

    private String externalAccountId;

    private String handle;

    private String displayName;

    private String profileUrl;

    private Long followers;
}