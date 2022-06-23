package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.UserResponse;
import com.example.javaweblabs.service.abstraction.UserResponsesService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ResponsesCommand implements Command {
    private final UserResponsesService userResponsesService;

    public ResponsesCommand(UserResponsesService userResponsesService) {
        this.userResponsesService = userResponsesService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<UserResponse> responseList = userResponsesService.findAll();
        request.setAttribute("responsesList", responseList);
        return "/WEB-INF/jsp/manager/responses.jsp";
    }
}
