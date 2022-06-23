package com.example.javaweblabs.service.implementation;

import com.example.javaweblabs.dao.abstraction.CashAccountDao;
import com.example.javaweblabs.dao.abstraction.DaoFactory;
import com.example.javaweblabs.dao.implementation.ConnectionPoolHolder;
import com.example.javaweblabs.dao.implementation.DefaultDaoFactory;
import com.example.javaweblabs.entity.CashAccount;
import com.example.javaweblabs.service.abstraction.CashAccountService;

import java.util.Optional;

public class CashAccountServiceImpl implements CashAccountService {
    private final DaoFactory daoFactory = DefaultDaoFactory.getInstance();

    @Override
    public Optional<CashAccount> findByUserId(Integer userId) {
        try (CashAccountDao cashAccountDao = daoFactory.getCashAccountDao(ConnectionPoolHolder.getConnection())) {
            return cashAccountDao.findByUserId(userId);
        }
    }

    @Override
    public boolean topUpBalance(Integer cashAccountUserId, Integer deposit) {
        try (CashAccountDao cashAccountDao = daoFactory.getCashAccountDao(ConnectionPoolHolder.getConnection())) {
            Optional<CashAccount> cashAccountOptional = cashAccountDao.findByUserId(cashAccountUserId);
            if (cashAccountOptional.isPresent()) {
                CashAccount cashAccount = cashAccountOptional.get();
                Integer currentBalance = cashAccount.getBalance();
                cashAccount.setBalance(currentBalance + deposit);
                return cashAccountDao.update(cashAccount);
            }
        }
        return false;
    }
}
