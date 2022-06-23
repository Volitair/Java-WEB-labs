package com.example.javaweblabs.service.implementation;

import com.example.javaweblabs.dao.abstraction.DaoFactory;
import com.example.javaweblabs.dao.abstraction.UserResponseDao;
import com.example.javaweblabs.dao.implementation.ConnectionPoolHolder;
import com.example.javaweblabs.dao.implementation.DefaultDaoFactory;
import com.example.javaweblabs.entity.UserResponse;
import com.example.javaweblabs.service.abstraction.UserResponsesService;

import java.util.List;

public class UserResponsesServiceImpl implements UserResponsesService {
    private final DaoFactory daoFactory = DefaultDaoFactory.getInstance();

    @Override
    public boolean create(UserResponse response) {
        try (UserResponseDao userResponseDao = daoFactory.getUserResponseDao(ConnectionPoolHolder.getConnection())) {
            return userResponseDao.create(response);
        }
    }

    @Override
    public List<UserResponse> findAll() {
        try (UserResponseDao userResponseDao = daoFactory.getUserResponseDao(ConnectionPoolHolder.getConnection())) {
            List<UserResponse> responses = userResponseDao.findAll();
            return responses;
        }
    }
}
