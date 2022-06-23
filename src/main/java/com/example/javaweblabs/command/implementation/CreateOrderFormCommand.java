package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;

import javax.servlet.http.HttpServletRequest;

public class CreateOrderFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/jsp/user/createOrderForm.jsp";
    }
}
