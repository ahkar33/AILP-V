package com.ace.ailpv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/student-home")
    public String showStudentHomePage() {
        return "/student/student-home";
    }

    @GetMapping("/student-group-chat")
    public String setupStudentGroupChat() {
        return "/student/student-group-chat";
    }

    @GetMapping("/student-public-chat")
    public String setupStudentPublicChat() {
        return "/student/STU-PBC-07.html";
    }

}
