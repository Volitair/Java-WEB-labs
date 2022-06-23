package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.service.abstraction.OrderService;

import javax.servlet.http.HttpServletRequest;

public class PayOrderCommand implements Command {
    private final OrderService orderService;

    public PayOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        orderService.payOrder(orderId);

        return "redirect:/app/home";
    }
}
