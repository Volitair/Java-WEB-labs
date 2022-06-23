package com.example.javaweblabs.dao.abstraction;

import java.sql.Connection;

public interface DaoFactory {
    UserDao getUserDao(Connection connection);

    OrderDao getOrderDao(Connection connection);

    CashAccountDao getCashAccountDao(Connection connection);

    UserResponseDao getUserResponseDao(Connection connection);
}
