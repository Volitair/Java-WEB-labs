package com.example.javaweblabs.service.implementation;

import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.service.abstraction.AuthService;
import com.example.javaweblabs.service.abstraction.UserService;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> loginUser(String login, String password) {
        Optional<User> user = userService.findUserByLogin(login);
        if (user.isPresent()) {
            if (password.equals(user.get().getPassword())) {
                return user;
            }
            return Optional.empty();
        }
        return Optional.empty();
    }
}
