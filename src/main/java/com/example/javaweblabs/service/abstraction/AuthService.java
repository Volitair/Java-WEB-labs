package com.example.javaweblabs.service.abstraction;

import com.example.javaweblabs.entity.User;

import java.util.Optional;

public interface AuthService {
    Optional<User> loginUser(String login, String password);
}
