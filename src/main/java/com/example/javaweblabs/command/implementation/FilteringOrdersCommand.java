package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.enums.OrderStatus;
import com.example.javaweblabs.service.abstraction.OrderService;
import com.example.javaweblabs.util.OrderFilterProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

public class FilteringOrdersCommand implements Command {
    private final OrderService orderService;

    public FilteringOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer masterId = null;
        if (!Objects.equals(request.getParameter("masterId"), "")) {
            masterId = Integer.valueOf(request.getParameter("masterId"));
        }
        OrderStatus orderStatus = null;
        if (!Objects.equals(request.getParameter("orderStatus"), "")) {
            orderStatus = OrderStatus.valueOf(request.getParameter("orderStatus"));
        }
        List<Order> orderList = orderService.findAll(new OrderFilterProperties(orderStatus, masterId));
        request.setAttribute("orderList", orderList);
        request.setAttribute("selectedMasterId", request.getParameter("masterId"));
        request.setAttribute("selectedOrderStatus", request.getParameter("orderStatus"));
        return "/app/orders";
    }
}
