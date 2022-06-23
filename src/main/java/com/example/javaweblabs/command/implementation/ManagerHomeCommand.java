package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;

import javax.servlet.http.HttpServletRequest;

public class ManagerHomeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/jsp/manager/home.jsp";
    }
}
