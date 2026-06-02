package com.sentinel.account.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class AccountResponse {

    private UUID id;
    private String platform;
    private String externalAccountId;
    private String handle;
    private String displayName;
    private String profileUrl;
    private String niche;
    private String subniche;
    private Long followers;
    private Long avgViews;
    private String language;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}