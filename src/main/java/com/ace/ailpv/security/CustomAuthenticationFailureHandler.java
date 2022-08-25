package com.ace.ailpv.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String error = "";
        
        // super.onAuthenticationFailure(request, response, exception);
        
        if(exception.getClass().isAssignableFrom(DisabledException.class)) 
            error = "disabled";
        else 
            error = "error";

        response.sendRedirect("/auth/login/?error=" + error);
    }
}
