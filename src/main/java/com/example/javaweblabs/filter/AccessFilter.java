package com.example.javaweblabs.filter;

import com.example.javaweblabs.context.AccessManager;
import com.example.javaweblabs.context.ApplicationContext;
import com.example.javaweblabs.enums.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/app/*")
public class AccessFilter implements Filter {
    private AccessManager accessManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        accessManager = ApplicationContext.getAccessManager();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);
        String uri = httpRequest.getPathInfo();
        Role role = null;
        if (session != null) {
            role = (Role) session.getAttribute("userRole");
        }
        if (accessManager.isAccessAllowed(uri, role)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
