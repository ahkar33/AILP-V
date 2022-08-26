package com.ace.ailpv.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Schedule;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.UserSchedule;
import com.ace.ailpv.service.ScheduleService;
import com.ace.ailpv.service.UserScheduleService;
import com.ace.ailpv.service.UserService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserScheduleService userScheduleService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/dashboard")
    public String setupTeacherDashboard(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        batchList = batchList.stream()
                .filter(batch -> batch.getIsActive())
                .collect(Collectors.toList());
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-DSB-01";
    }

    @GetMapping("/student-table")
    public String setupStudentTable(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        User teacherInfo = userService.getUserById(teacherId);
        model.addAttribute("studentList", userService.getStudentListByTeacherId(teacherInfo.getId()));
        return "/teacher/TCH-STB-11";
    }

    @GetMapping("/teacher-public-chat")
    public String setupTeacherPublicChat(HttpSession session, ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        User teacherInfo = userService.getUserById(teacherId);
        List<Batch> batchList = userService.getTeacherBatchListById(teacherInfo.getId());
        batchList = batchList.stream().filter(batch -> batch.getIsActive()).collect(Collectors.toList());
        Batch firstBatch = batchList.get(0);
        model.addAttribute("userId", teacherInfo.getId());
        model.addAttribute("username", teacherInfo.getName());
        model.addAttribute("batchId", firstBatch.getId());
        model.addAttribute("batchName", firstBatch.getName());
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-PBC-05";
    }

    @GetMapping("/chatWithBatch/{batchId}/{batchName}")
    public String setupChatWithBatch(
            @PathVariable("batchId") Long batchId,
            @PathVariable("batchName") String batchName,
            HttpSession session,
            ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        User teacherInfo = userService.getUserById(teacherId);
        model.addAttribute("userId", teacherInfo.getId());
        model.addAttribute("username", teacherInfo.getName());
        model.addAttribute("batchId", batchId);
        model.addAttribute("batchName", batchName);
        List<Batch> batchList = userService.getTeacherBatchListById(teacherInfo.getId());
        batchList = batchList.stream().filter(batch -> batch.getIsActive()).collect(Collectors.toList());
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-CWB-06";
    }

    @GetMapping("/modifyAttendance")
    public String setupModifyAttendance(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-MDA-07";
    }

    @GetMapping("/attendance-table")
    public String setupAttendanceTable(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        List<UserSchedule> userScheduleList = userScheduleService.getAllUserSchedules();
        model.addAttribute("userScheduleList", userScheduleList);
        model.addAttribute("batchList", batchList);
        model.addAttribute("data", new UserSchedule());
        return "/teacher/TCH-ATB-08";
    }

    @PostMapping("/searchUserScheduleList")
    public String searchUserScheduleList(@ModelAttribute("data") UserSchedule userSchedule, ModelMap model,
            HttpSession session) {
        Long batchId = userSchedule.getUser().getBatchList().get(0).getId();
        if (userSchedule.getDate() != null) {
            Schedule resSchedule = scheduleService.getScheduleByDate(userSchedule.getDate());
            if (resSchedule == null) {
                String teacherId = (String) session.getAttribute("uid");
                List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
                model.addAttribute("userScheduleList", new ArrayList<>());
                model.addAttribute("batchList", batchList);
                model.addAttribute("data", new UserSchedule());
                return "/teacher/TCH-ATB-08";

            }
            List<UserSchedule> list = userScheduleService.getUserScheduleListByBatchIdAndScheduleId(batchId,
                    resSchedule.getId());
            String teacherId = (String) session.getAttribute("uid");
            List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
            model.addAttribute("userScheduleList", list);
            model.addAttribute("batchList", batchList);
            model.addAttribute("data", new UserSchedule());
            return "/teacher/TCH-ATB-08";
        } else {
            List<UserSchedule> list = userScheduleService.getUserScheduleListByBatchIdOrScheduleId(batchId);
            String teacherId = (String) session.getAttribute("uid");
            List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
            model.addAttribute("userScheduleList", list);
            model.addAttribute("batchList", batchList);
            model.addAttribute("data", userSchedule);
            return "/teacher/TCH-ATB-08";
        }
    }

    @GetMapping("/postResource")
    public String setupPostResource(HttpSession session, ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-UPR-02";
    }

}
