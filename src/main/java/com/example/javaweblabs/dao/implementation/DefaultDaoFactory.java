package com.example.javaweblabs.dao.implementation;

import com.example.javaweblabs.dao.abstraction.*;

import java.sql.Connection;

public class DefaultDaoFactory implements DaoFactory {
    private static volatile DefaultDaoFactory daoFactory;

    private DefaultDaoFactory() {
    }

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DefaultDaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new DefaultDaoFactory();
                }
            }
        }
        return daoFactory;
    }

    @Override
    public UserDao getUserDao(Connection connection) {
        return new DefaultUserDao(connection);
    }

    @Override
    public OrderDao getOrderDao(Connection connection) {
        return new DefaultOrderDao(connection);
    }

    @Override
    public CashAccountDao getCashAccountDao(Connection connection) {
        return new DefaultCashAccountDao(connection);
    }

    @Override
    public UserResponseDao getUserResponseDao(Connection connection) {
        return new DefaultUserResponseDao(connection);
    }
}
