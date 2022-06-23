package com.example.javaweblabs.dao.abstraction;

import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<Integer, User>, AutoCloseable {
    Optional<User> findByLogin(String login);

    @Override
    void close();

    List<User> findAllByRole(Role role);
}
