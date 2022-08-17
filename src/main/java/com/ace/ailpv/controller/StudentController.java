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

}
