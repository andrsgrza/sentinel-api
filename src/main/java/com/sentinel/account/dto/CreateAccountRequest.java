package com.sentinel.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {

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
}