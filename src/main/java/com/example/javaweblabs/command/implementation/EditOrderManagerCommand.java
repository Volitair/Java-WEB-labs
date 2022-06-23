package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.enums.OrderStatus;
import com.example.javaweblabs.service.abstraction.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class EditOrderManagerCommand implements Command {
    private final OrderService orderService;

    public EditOrderManagerCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        Optional<Order> optionalOrder = orderService.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            String masterId = request.getParameter("masterId");
            Integer newPrice = Integer.valueOf(request.getParameter("price"));
            OrderStatus newOrderStatus = OrderStatus.valueOf(request.getParameter("orderStatus"));
            if (masterId.equals("")) {
                order.setMaster(null);
            } else {
                Integer newMasterId = Integer.valueOf(masterId);
                if (!newMasterId.equals(order.getMaster().getId())) {
                    User newMaster = new User();
                    newMaster.setId(newMasterId);
                    order.setMaster(newMaster);
                }
            }
            order.setOrderStatus(newOrderStatus);
            order.setPrice(newPrice);
        }
        return "redirect:/app/orders";
    }
}
