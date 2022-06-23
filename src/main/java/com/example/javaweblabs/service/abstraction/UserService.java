package com.example.javaweblabs.service.abstraction;

import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findUserById(Integer userId);

    Optional<User> findUserByLogin(String userLogin);

    List<User> findAllByRole(Role role);
}
