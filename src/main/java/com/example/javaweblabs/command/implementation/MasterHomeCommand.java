package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.service.abstraction.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class MasterHomeCommand implements Command {
    private final OrderService orderService;


    public MasterHomeCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer masterId = (Integer) session.getAttribute("userId");
        List<Order> orders = orderService.getMasterOrders(masterId);
        request.setAttribute("orderList", orders);
        return "/WEB-INF/jsp/master/home.jsp";
    }
}
