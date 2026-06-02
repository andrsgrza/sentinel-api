package com.sentinel.account;

import com.sentinel.account.dto.AccountResponse;
import com.sentinel.account.dto.CreateAccountRequest;

public class AccountMapper {

    private AccountMapper() {
    }

    public static AccountEntity toEntity(CreateAccountRequest request) {
        return AccountEntity.builder()
                .platform(request.getPlatform())
                .externalAccountId(request.getExternalAccountId())
                .handle(request.getHandle())
                .displayName(request.getDisplayName())
                .profileUrl(request.getProfileUrl())
                .niche(request.getNiche())
                .subniche(request.getSubniche())
                .followers(request.getFollowers())
                .avgViews(request.getAvgViews())
                .language(request.getLanguage())
                .build();
    }

    public static AccountResponse toResponse(AccountEntity entity) {
        return AccountResponse.builder()
                .id(entity.getId())
                .platform(entity.getPlatform())
                .externalAccountId(entity.getExternalAccountId())
                .handle(entity.getHandle())
                .displayName(entity.getDisplayName())
                .profileUrl(entity.getProfileUrl())
                .niche(entity.getNiche())
                .subniche(entity.getSubniche())
                .followers(entity.getFollowers())
                .avgViews(entity.getAvgViews())
                .language(entity.getLanguage())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}