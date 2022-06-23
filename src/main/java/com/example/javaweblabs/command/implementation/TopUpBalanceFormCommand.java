package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.CashAccount;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.service.abstraction.CashAccountService;
import com.example.javaweblabs.service.abstraction.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class TopUpBalanceFormCommand implements Command {
    private final UserService userService;
    private final CashAccountService cashAccountService;

    public TopUpBalanceFormCommand(UserService userService, CashAccountService cashAccountService) {
        this.userService = userService;
        this.cashAccountService = cashAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        Optional<User> user = userService.findUserById(userId);
        Optional<CashAccount> cashAccount = cashAccountService.findByUserId(userId);

        request.setAttribute("user", user.get());
        request.setAttribute("userBalance", cashAccount.get());

        return "/WEB-INF/jsp/manager/topUpUserBalanceForm.jsp";
    }
}
