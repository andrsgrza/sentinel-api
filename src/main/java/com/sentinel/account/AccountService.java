package com.sentinel.account;

import com.sentinel.account.dto.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public List<AccountEntity> findAll() {
        return accountRepository.findAll();
    }

    public AccountEntity createOrUpdate(CreateAccountRequest request) {
        Instant now = Instant.now();

        AccountEntity account = findExistingAccount(request)
                .orElseGet(() -> AccountEntity.builder()
                        .id(UUID.randomUUID())
                        .platform(request.getPlatform())
                        .externalAccountId(request.getExternalAccountId())
                        .handle(request.getHandle())
                        .createdAt(now)
                        .active(true)
                        .build());

        account.setDisplayName(request.getDisplayName());
        account.setProfileUrl(request.getProfileUrl());
        account.setNiche(request.getNiche());
        account.setSubniche(request.getSubniche());
        account.setFollowers(request.getFollowers());
        account.setAvgViews(request.getAvgViews());
        account.setLanguage(request.getLanguage());
        account.setUpdatedAt(now);

        return accountRepository.save(account);
    }

    private java.util.Optional<AccountEntity> findExistingAccount(CreateAccountRequest request) {
        if (request.getExternalAccountId() != null && !request.getExternalAccountId().isBlank()) {
            return accountRepository.findByPlatformAndExternalAccountId(
                    request.getPlatform(),
                    request.getExternalAccountId()
            );
        }

        return accountRepository.findByPlatformAndHandle(
                request.getPlatform(),
                request.getHandle()
        );
    }
}