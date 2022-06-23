package com.example.javaweblabs.listener;

import com.example.javaweblabs.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
