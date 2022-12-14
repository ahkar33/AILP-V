package com.ace.ailpv.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.UserService;

@Configuration
public class RemembermeSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        User user = userService.getUserById(authentication.getName());

        request.getSession().setAttribute("uid", user.getId());
        request.getSession().setAttribute("name", user.getName());
        request.getSession().setAttribute("profile_pic", user.getProfile_pic());

        if (user.getRole().equals("ROLE_ADMIN")) {
            response.sendRedirect("/admin/dashboard");
        }
        if (user.getRole().equals("ROLE_TEACHER")) {
            response.sendRedirect("/teacher/dashboard");
        }
        if (user.getRole().equals("ROLE_STUDENT")) {
            response.sendRedirect("/student/student-home");
        }

    }
}
