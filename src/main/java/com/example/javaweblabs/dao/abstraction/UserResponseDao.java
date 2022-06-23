package com.example.javaweblabs.dao.abstraction;

import com.example.javaweblabs.entity.UserResponse;

public interface UserResponseDao extends CrudDao<Integer, UserResponse>, AutoCloseable {

    @Override
    void close();
}
