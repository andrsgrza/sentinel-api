package com.sentinel.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByPlatformAndExternalAccountId(
            String platform,
            String externalAccountId
    );

    Optional<AccountEntity> findByPlatformAndHandle(
            String platform,
            String handle
    );
}