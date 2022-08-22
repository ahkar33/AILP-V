package com.ace.ailpv.security;

import java.io.IOException;
import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.UserService;

@Configuration
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    UserService userService;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        System.out.println("auth invoked");
        User user = userService.getUserById(authentication.getName());
        

        request.getSession().setAttribute("uid", user.getId());
        request.getSession().setAttribute("name", user.getName());
        request.getSession().setAttribute("profile_pic", user.getProfile_pic());

        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        String url = "/login?error=true";
        // fetch the roles from Authenication object
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority a : authorities) {
            
            roles.add(a.getAuthority());
        }

        
        // check user role and decide the redirect url
        if (roles.contains("ROLE_ADMIN")) 
            url = "/admin/dashboard";
        else if(roles.contains("ROLE_TEACHER")) 
            url = "/teacher/teacher-public-chat"; 
        else if (roles.contains("ROLE_STUDENT")) 
            url = "/student/student-home";
           
        return url;
    }
}
