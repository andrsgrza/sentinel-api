package com.sentinel.account;

import com.sentinel.account.dto.AccountResponse;
import com.sentinel.account.dto.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll()
                .stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    @PostMapping
    public AccountResponse create(@RequestBody CreateAccountRequest request) {
        AccountEntity account = accountService.create(request);
        return AccountMapper.toResponse(account);
    }
}