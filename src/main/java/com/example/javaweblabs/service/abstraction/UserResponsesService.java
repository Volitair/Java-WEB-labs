package com.example.javaweblabs.service.abstraction;

import com.example.javaweblabs.entity.UserResponse;

import java.util.List;

public interface UserResponsesService {
    boolean create(UserResponse response);

    List<UserResponse> findAll();
}
