package com.example.javaweblabs.controller;

import com.example.javaweblabs.command.CommandHandler;
import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "frontController", value = "/app/*")
public class FrontController extends HttpServlet {
    private CommandHandler commandHandler;

    @Override
    public void init() throws ServletException {
        commandHandler = ApplicationContext.getCommandHandler();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        Command command = commandHandler.getCommand(path);
        String page = command.execute(request);
        if (page.contains("redirect:")) {
            String newPath = page.replace("redirect:", "");
            response.sendRedirect(newPath);
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
