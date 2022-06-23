package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.service.abstraction.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

public class CreateOrderCommand implements Command {
    private final OrderService orderService;

    public CreateOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        Order order = new Order();
        User user = new User();
        user.setId(userId);
        order.setUser(user);
        order.setStartDate(LocalDate.now());
        String description = request.getParameter("description");
        order.setDescription(description);
        orderService.create(order);

        return "redirect:/app/home";
    }
}
