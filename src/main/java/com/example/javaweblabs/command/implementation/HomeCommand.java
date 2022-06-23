package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class HomeCommand implements Command {
    private final HashMap<Role, String> homePageUrlsByRole;

    public HomeCommand() {
        homePageUrlsByRole = new HashMap<>();
        homePageUrlsByRole.put(Role.USER, "/app/user/home");
        homePageUrlsByRole.put(Role.MANAGER, "/app/manager/home");
        homePageUrlsByRole.put(Role.MASTER, "/app/master/home");
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("userRole");
        if (role == null) return "redirect:/app/login";
        return homePageUrlsByRole.get(role);
    }
}
