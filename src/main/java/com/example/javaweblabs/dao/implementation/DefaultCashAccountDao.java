package com.example.javaweblabs.dao.implementation;

import com.example.javaweblabs.dao.abstraction.CashAccountDao;
import com.example.javaweblabs.entity.CashAccount;
import com.example.javaweblabs.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DefaultCashAccountDao implements CashAccountDao {
    private final Connection connection;

    public DefaultCashAccountDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<CashAccount> findByUserId(Integer userId) {
        String sql = "SELECT * FROM cash_accounts WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                CashAccount cashAccount = new CashAccount();
                cashAccount.setUser(user);
                cashAccount.setId(resultSet.getInt("id"));
                cashAccount.setBalance(resultSet.getInt("balance"));
                return Optional.of(cashAccount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(CashAccount cashAccount) {
        boolean updated = false;
        String sql = "UPDATE cash_accounts SET balance=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cashAccount.getBalance());
            statement.setInt(2, cashAccount.getId());
            updated = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
