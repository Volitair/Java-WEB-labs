package com.example.javaweblabs.service.implementation;

import com.example.javaweblabs.dao.abstraction.DaoFactory;
import com.example.javaweblabs.dao.abstraction.UserDao;
import com.example.javaweblabs.dao.implementation.ConnectionPoolHolder;
import com.example.javaweblabs.dao.implementation.DefaultDaoFactory;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.enums.Role;
import com.example.javaweblabs.service.abstraction.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final DaoFactory daoFactory = DefaultDaoFactory.getInstance();

    public List<User> findAll() {
        try (UserDao userDao = daoFactory.getUserDao(ConnectionPoolHolder.getConnection())) {
            return userDao.findAll();
        }
    }

    @Override
    public Optional<User> findUserById(Integer userId) {
        try (UserDao userDao = daoFactory.getUserDao(ConnectionPoolHolder.getConnection())) {
            return userDao.findById(userId);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String userLogin) {
        try (UserDao userDao = daoFactory.getUserDao(ConnectionPoolHolder.getConnection())) {
            return userDao.findByLogin(userLogin);
        }
    }

    @Override
    public List<User> findAllByRole(Role role) {
        try (UserDao userDao = daoFactory.getUserDao(ConnectionPoolHolder.getConnection())) {
            return userDao.findAllByRole(role);
        }
    }
}
