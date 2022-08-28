package com.ace.ailpv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ailpv.SecretConfigProperties;
import com.ace.ailpv.entity.User;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SecretConfigProperties secretConfigProperties;
    @GetMapping("/login")
    public String setupLogin(ModelMap model) {
        model.addAttribute("user", new User());
        System.out.println(secretConfigProperties.getDbpass());
        return "/USR-LGN-01";
    }

}
