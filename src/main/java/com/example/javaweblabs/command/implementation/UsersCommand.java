package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.enums.Role;
import com.example.javaweblabs.service.abstraction.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UsersCommand implements Command {
    private final UserService userService;

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<User> users = userService.findAllByRole(Role.USER);
        request.setAttribute("usersList", users);
        return "/WEB-INF/jsp/manager/users.jsp";
    }
}
