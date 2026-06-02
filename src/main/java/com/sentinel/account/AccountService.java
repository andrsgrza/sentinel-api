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

    public AccountEntity create(CreateAccountRequest request) {
        Instant now = Instant.now();

        AccountEntity account = AccountMapper.toEntity(request);

        account.setId(UUID.randomUUID());
        account.setActive(true);
        account.setCreatedAt(now);
        account.setUpdatedAt(now);

        return accountRepository.save(account);
    }
}