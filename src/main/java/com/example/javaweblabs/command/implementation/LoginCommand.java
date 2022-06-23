package com.example.javaweblabs.command.implementation;

import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.service.abstraction.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private final AuthService authService;

    public LoginCommand(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || login.equals("") || password == null || password.equals("")) {
            return "/WEB-INF/jsp/login.jsp";
        }

        Optional<User> user = authService.loginUser(login, password);
        if (user.isPresent()) {
            setUserAttributeInSession(request, user.get());
            return "redirect:/app/home";
        }

        return "redirect:/app/login";
    }

    private void setUserAttributeInSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("userLogin", user.getLogin());
        session.setAttribute("userRole", user.getRole());
    }
}
