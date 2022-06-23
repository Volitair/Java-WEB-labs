package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;

import javax.servlet.http.HttpServletRequest;

public class CreateResponseFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        request.setAttribute("orderId", orderId);
        return "/WEB-INF/jsp/user/createResponseForm.jsp";
    }
}
