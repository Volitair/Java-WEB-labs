package com.example.javaweblabs.dao.abstraction;

import com.example.javaweblabs.entity.CashAccount;

import java.util.Optional;

public interface CashAccountDao extends AutoCloseable {
    Optional<CashAccount> findByUserId(Integer userId);

    boolean update(CashAccount cashAccount);

    @Override
    void close();
}
