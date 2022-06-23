package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.CashAccount;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.service.abstraction.CashAccountService;
import com.example.javaweblabs.service.abstraction.OrderService;
import com.example.javaweblabs.service.abstraction.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserHomeCommand implements Command {
    private final UserService userService;
    private final OrderService orderService;
    private final CashAccountService cashAccountService;

    public UserHomeCommand(UserService userService, OrderService orderService, CashAccountService cashAccountService) {
        this.userService = userService;
        this.orderService = orderService;
        this.cashAccountService = cashAccountService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userLogin = String.valueOf(session.getAttribute("userLogin"));
        User user = userService.findUserByLogin(userLogin).orElseThrow(IllegalAccessError::new);
        Integer userId = user.getId();
        List<Order> orderList = orderService.getUserOrders(userId);
        request.setAttribute("orderList", orderList);

        CashAccount cashAccount = cashAccountService.findByUserId(userId).get();
        request.setAttribute("cashAccount", cashAccount);

        return "/WEB-INF/jsp/user/home.jsp";
    }
}
