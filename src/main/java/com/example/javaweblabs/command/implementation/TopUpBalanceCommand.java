package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.CashAccount;
import com.example.javaweblabs.service.abstraction.CashAccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class TopUpBalanceCommand implements Command {
    private final CashAccountService cashAccountService;

    public TopUpBalanceCommand(CashAccountService cashAccountService) {
        this.cashAccountService = cashAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        Integer deposit = Integer.valueOf(request.getParameter("deposit"));
        Optional<CashAccount> cashAccount = cashAccountService.findByUserId(userId);
        if (cashAccount.isPresent()) {
            cashAccountService.topUpBalance(userId, deposit);
        }

        return "redirect:/app/users";
    }
}
