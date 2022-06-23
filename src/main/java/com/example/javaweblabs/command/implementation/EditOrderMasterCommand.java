package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.enums.OrderStatus;
import com.example.javaweblabs.service.abstraction.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class EditOrderMasterCommand implements Command {
    private final OrderService orderService;

    public EditOrderMasterCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        Optional<Order> optionalOrder = orderService.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            OrderStatus newOrderStatus = OrderStatus.valueOf(request.getParameter("orderStatus"));
            orderService.changeOrderStatus(order, newOrderStatus);
            orderService.update(order);
        }

        return "redirect:/app/home";
    }
}
