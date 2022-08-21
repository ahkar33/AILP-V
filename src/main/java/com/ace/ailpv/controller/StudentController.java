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

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private BatchHasResourceService batchHasResourceService;

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

    @GetMapping("/getResources")
    public String getResources(ModelMap model, HttpSession session) {
        User studentInfo = (User) session.getAttribute("userInfo");
        Long studentBatchId = studentInfo.getBatchList().iterator().next().getId();
        List<BatchHasResource> batchHasResourceList = batchHasResourceService
                .getAllBatchHasResourcesByBatchId(studentBatchId);
        // for(BatchHasResource batchHasResource: BatchHasResourceList) {
        //     System.out.println(batchHasResource.getResource().getName());
        //     System.out.println(batchHasResource.getSchedule());
        //     System.out.println("----------------------------------------");
        // }
        model.addAttribute("batchHasResourceList", batchHasResourceList);
        return "/student/STU-REC-09";
    }

}
