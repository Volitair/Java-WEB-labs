package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.enums.OrderStatus;
import com.example.javaweblabs.enums.Role;
import com.example.javaweblabs.service.abstraction.OrderService;
import com.example.javaweblabs.service.abstraction.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class OrdersCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;
    private final HashMap<String, Comparator<Order>> sortingRules;

    public OrdersCommand(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
        sortingRules = new HashMap<>();
        sortingRules.put("start-date", Comparator.comparing(Order::getStartDate));
        sortingRules.put("order-status", Comparator.comparing(Order::getOrderStatus));
        sortingRules.put("price", Comparator.comparing(Order::getPrice, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    @Override
    public String execute(HttpServletRequest request) {
        String fieldName = request.getParameter("sortBy");
        List<Order> orderList = (List<Order>) request.getAttribute("orderList");
        if (orderList == null) {
            orderList = orderService.findAll();
        }
        if (fieldName != null && !fieldName.equals("")) {
            Comparator<Order> comparator = sortingRules.get(fieldName);
            if (comparator != null) orderList.sort(comparator);
        }
        List<User> mastersList = userService.findAllByRole(Role.MASTER);
        request.setAttribute("sortingChecked", fieldName);
        request.setAttribute("masters", mastersList);
        request.setAttribute("orderStatuses", OrderStatus.values());
        request.setAttribute("orderList", orderList);
        return "/WEB-INF/jsp/manager/orders.jsp";
    }
}
