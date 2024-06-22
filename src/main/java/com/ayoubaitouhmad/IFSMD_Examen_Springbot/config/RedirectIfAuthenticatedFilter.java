package com.ayoubaitouhmad.IFSMD_Examen_Springbot.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


import java.io.IOException;

@Component
public class RedirectIfAuthenticatedFilter extends GenericFilterBean {

    private static final String[] REDIRECT_ROUTES = {"/login", "/register"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            String path = httpServletRequest.getRequestURI();
            for (String route : REDIRECT_ROUTES) {
                if (path.equalsIgnoreCase(route)) {
                    httpServletResponse.sendRedirect("/articles"); // Redirect to a default page
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }


}
