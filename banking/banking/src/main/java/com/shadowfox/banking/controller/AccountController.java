package com.shadowfox.banking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shadowfox.banking.model.Account;
import com.shadowfox.banking.repository.AccountRepository;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return repository.save(account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }
}