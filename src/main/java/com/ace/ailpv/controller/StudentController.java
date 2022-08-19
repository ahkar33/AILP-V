package com.ace.ailpv.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ailpv.entity.User;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/student-home")
    public String showStudentHomePage() {
        return "/student/STU-HOM-01";
    }

    @GetMapping("/student-public-chat")
    public String setupStudentPublicChat(HttpSession session, ModelMap model) {
        User userInfo = (User) session.getAttribute("userInfo");
        userInfo.setBatchId(userInfo.getBatchList().iterator().next().getId().toString());
        userInfo.setBatchName(userInfo.getBatchList().iterator().next().getName().toLowerCase());
        model.addAttribute("userId", userInfo.getId());
        model.addAttribute("username", userInfo.getName());
        model.addAttribute("batchId", userInfo.getBatchId());
        model.addAttribute("batchName", userInfo.getBatchName());
        return "/student/STU-PBC-07";
    }

}
