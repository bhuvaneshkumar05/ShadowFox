package com.shadowfox.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shadowfox.banking.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}