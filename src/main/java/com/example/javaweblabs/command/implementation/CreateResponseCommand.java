package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.entity.UserResponse;
import com.example.javaweblabs.service.abstraction.OrderService;
import com.example.javaweblabs.service.abstraction.UserResponsesService;

import javax.servlet.http.HttpServletRequest;

public class CreateResponseCommand implements Command {
    private final OrderService orderService;
    private final UserResponsesService userResponsesService;

    public CreateResponseCommand(OrderService orderService, UserResponsesService userResponsesService) {
        this.orderService = orderService;
        this.userResponsesService = userResponsesService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        Order order = orderService.findById(orderId).get();
        String response = request.getParameter("response");
        UserResponse userResponse = new UserResponse();
        userResponse.setResponse(response);
        userResponse.setUser(order.getUser());
        userResponse.setMaster(order.getMaster());
        userResponsesService.create(userResponse);

        return "redirect:/app/home";
    }
}
