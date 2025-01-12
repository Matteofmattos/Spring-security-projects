package com.matteof.mattos.springSecurityOne.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class MySecurityFilter extends OncePerRequestFilter {

    private final String HEADER_LOGIN = "x-myfilter-login";
    private final String HEADER_PASSWORD = "x-myfilter-password";

    private final AuthenticationManager authenticationManager;

    public MySecurityFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("Before filter...");

        if (!Collections.list(request.getHeaderNames()).contains(HEADER_LOGIN) ||
                !Collections.list(request.getHeaderNames()).contains(HEADER_PASSWORD)){
            System.out.println("No headers found...");
            filterChain.doFilter(request,response);
        }

        String username = request.getHeader(HEADER_LOGIN);
        String password = request.getHeader(HEADER_PASSWORD);

        //My authentication request...
        MySecurityAuthentication authenticationRequest = MySecurityAuthentication.unauthenticated(username, password);

        try{

            Authentication authenticate = authenticationManager.authenticate(authenticationRequest);
            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            emptyContext.setAuthentication(authenticationRequest);
            SecurityContextHolder.setContext(emptyContext);

            filterChain.doFilter(request,response);

        } catch (AuthenticationException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().println("# Invalid credentials!");
        }

        System.out.println("After filter...");

    }
}
