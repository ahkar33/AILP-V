package com.ace.ailpv.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.UserService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private UserService userService;

    @GetMapping("/student-table")
    public String setupStudentTable(ModelMap model, HttpSession session) {
        User teacher = (User) session.getAttribute("userInfo");
        model.addAttribute("studentList", userService.getStudentListByTeacherId(teacher.getId()));
        return "/teacher/TCH-STB-11";
    }

}
