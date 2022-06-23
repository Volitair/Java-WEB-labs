package com.example.javaweblabs.service.abstraction;

import com.example.javaweblabs.entity.CashAccount;

import java.util.Optional;

public interface CashAccountService {
    Optional<CashAccount> findByUserId(Integer userId);

    boolean topUpBalance(Integer cashAccountUserId, Integer deposit);
}
