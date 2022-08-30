package com.ace.ailpv.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ailpv.entity.Assignment;
import com.ace.ailpv.entity.AssignmentAnswer;
import com.ace.ailpv.entity.BatchHasResource;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.AssignmentAnswerService;
import com.ace.ailpv.service.AssignmentService;
import com.ace.ailpv.service.BatchHasResourceService;
import com.ace.ailpv.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private BatchHasResourceService batchHasResourceService;

    //added by me
    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentAnswerService assignmentAnswerService;

    @Autowired
    private UserService usersService;


    @GetMapping("/student-home")
    public String showStudentHomePage() {
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

    //added by me
    @GetMapping("/studentAssignment")
    public String studentAssignment(ModelMap model, HttpSession session){
        String studentId =  (String) session.getAttribute("uid");
        User studentInfo = usersService.getUserById(studentId);
        Long studentBatchId = studentInfo.getBatchList().iterator().next().getId();
        List<Assignment> assignmentList = assignmentService.getAllAssignmentByBatchId(studentBatchId);
        model.addAttribute("assignmentList", assignmentList);      
        return "/student/STU-ASG-00";
    }

    @PostMapping("/submitAssignment")
    public String submitAssignment(@RequestParam("answerFile") MultipartFile multipartFileName
                , @RequestParam("assignmentId") Long id
                , @RequestParam("questionFileId") Long fileId
                , HttpSession session){
        String studentId =  (String) session.getAttribute("uid");
        User student = usersService.getUserById(studentId);
        String studentName = student.getName();
        String fileName = multipartFileName.getOriginalFilename();
        Long assignmentId = id;
        AssignmentAnswer answer = new AssignmentAnswer();
        answer.setAnswerFile(fileName);
        answer.setQuestion_file_id(fileId);
        answer.setStudent_name(studentName);
        answer.setAssignment_id(assignmentId);
        LocalDateTime now = LocalDateTime.now();
        answer.setSubmitTime(now);

        assignmentAnswerService.addStudentAnswer(answer);
        
        return "redirect:/student/studentAssignment";
    }

}
