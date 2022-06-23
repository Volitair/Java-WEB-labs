package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.enums.OrderStatus;
import com.example.javaweblabs.enums.Role;
import com.example.javaweblabs.service.abstraction.OrderService;
import com.example.javaweblabs.service.abstraction.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditOrderFormCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;

    public EditOrderFormCommand(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Role userRole = (Role) request.getSession().getAttribute("userRole");
        if (userRole == null) return "redirect:/app/login";
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        Optional<Order> optionalOrder = orderService.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            ArrayList<OrderStatus> possibleNewOrderStatuses = new ArrayList<>(orderService.getPossibleNewStatuses(order.getOrderStatus(), userRole));
            List<User> masters = userService.findAllByRole(Role.MASTER);

            request.setAttribute("masters", masters);
            request.setAttribute("possibleNewOrderStatuses", possibleNewOrderStatuses);
            request.setAttribute("order", order);
        }

        return "/WEB-INF/jsp/editOrderForm.jsp";
    }
}
