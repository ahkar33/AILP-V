package com.ace.ailpv.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ailpv.entity.BatchHasResource;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.BatchHasResourceService;
import com.ace.ailpv.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private BatchHasResourceService batchHasResourceService;

    @Autowired
    private UserService usersService;

    @GetMapping("/student-home")
    public String showStudentHomePage(HttpSession session, ModelMap model) {
        String studentId =  (String) session.getAttribute("uid");
        User userInfo = usersService.getUserById(studentId);
        userInfo.setBatchId(userInfo.getBatchList().iterator().next().getId().toString());
        model.addAttribute("userId", userInfo.getId());
        model.addAttribute("batchId", userInfo.getBatchId());
        return "/student/STU-HOM-01";
    }

    @GetMapping("/student-public-chat")
    public String setupStudentPublicChat(HttpSession session, ModelMap model) {
        String studentId =  (String) session.getAttribute("uid");
        User userInfo = usersService.getUserById(studentId);
        userInfo.setBatchId(userInfo.getBatchList().iterator().next().getId().toString());
        userInfo.setBatchName(userInfo.getBatchList().iterator().next().getName());
        model.addAttribute("userId", userInfo.getId());
        model.addAttribute("username", userInfo.getName());
        model.addAttribute("batchId", userInfo.getBatchId());
        model.addAttribute("batchName", userInfo.getBatchName());
        return "/student/STU-PBC-07";
    }

    @GetMapping("/getResources")
    public String getResources(ModelMap model, HttpSession session) {
        String studentId =  (String) session.getAttribute("uid");
        User studentInfo = usersService.getUserById(studentId);
        Long studentBatchId = studentInfo.getBatchList().iterator().next().getId();
        List<BatchHasResource> batchHasResourceList = batchHasResourceService
                .getAllBatchHasResourcesByBatchId(studentBatchId);
        model.addAttribute("batchHasResourceList", batchHasResourceList);
        return "/student/STU-REC-09";
    }

}
