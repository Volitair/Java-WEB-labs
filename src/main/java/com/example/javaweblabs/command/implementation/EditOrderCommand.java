package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class EditOrderCommand implements Command {
    private final HashMap<Role, String> editOrderStrategies;

    public EditOrderCommand() {
        editOrderStrategies = new HashMap<>();
        editOrderStrategies.put(Role.MANAGER, "/app/orders/edit/manager");
        editOrderStrategies.put(Role.MASTER, "/app/orders/edit/master");
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("userRole");
        if (role == null) return "redirect:/app/login";
        return editOrderStrategies.get(role);
    }
}
