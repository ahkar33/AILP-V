package com.ace.ailpv.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.BatchHasResource;
import com.ace.ailpv.entity.BatchHasVideo;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.service.BatchHasResourceService;
import com.ace.ailpv.service.BatchHasVideoService;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.UserService;
import com.ace.ailpv.service.VideoService;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private BatchHasResourceService batchHasResourceService;

    @Autowired
    private BatchHasVideoService batchHasVideoService;

    @Autowired
    private UserService usersService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private VideoService videoService;

    @GetMapping("/student-home")
    public String showStudentHomePage(HttpSession session, ModelMap model) {
        String studentId = (String) session.getAttribute("uid");
        User userInfo = usersService.getUserById(studentId);
        userInfo.setBatchId(userInfo.getBatchList().iterator().next().getId().toString());
        model.addAttribute("userId", userInfo.getId());
        model.addAttribute("batchId", userInfo.getBatchId());
        return "/student/STU-HOM-01";
    }

    @GetMapping("/student-public-chat")
    public String setupStudentPublicChat(HttpSession session, ModelMap model) {
        String studentId = (String) session.getAttribute("uid");
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
        String studentId = (String) session.getAttribute("uid");
        User studentInfo = usersService.getUserById(studentId);
        Long studentBatchId = studentInfo.getBatchList().iterator().next().getId();
        List<BatchHasResource> batchHasResourceList = batchHasResourceService
                .getAllBatchHasResourcesByBatchId(studentBatchId);
        model.addAttribute("batchHasResourceList", batchHasResourceList);
        return "/student/STU-REC-09";
    }

    @GetMapping("/getVideos")
    public String getVideos(ModelMap model, HttpSession session) {
        String studentId = (String) session.getAttribute("uid");
        User studentInfo = usersService.getUserById(studentId);
        Long studentBatchId = studentInfo.getBatchList().iterator().next().getId();
        Batch studentBatch = batchService.getBatchById(studentBatchId);
        List<BatchHasVideo> batchHasVideoList = batchHasVideoService
                .getAllBatchHasVideoByBatchId(studentBatchId);
        
        if (studentInfo.getLastWatchVideoId() != null) {
            Video video = videoService.getVideoById(studentInfo.getLastWatchVideoId());
            model.addAttribute("video", video);
        }

        model.addAttribute("courseName", studentBatch.getBatchCourse().getName());
        model.addAttribute("batchHasVideoList", batchHasVideoList);
        return "/student/STU-VID-06";
    }

    @GetMapping("/showClickedVideo/{courseName}/{videoId}")
    public String showClickedVideo(HttpSession session, ModelMap model, @PathVariable("videoId") String videoId,
            @PathVariable("courseName") String courseName) {
        String studentId = (String) session.getAttribute("uid");
        User studentInfo = usersService.getUserById(studentId);
        Long studentBatchId = studentInfo.getBatchList().iterator().next().getId();
        List<BatchHasVideo> batchHasVideoList = batchHasVideoService
                .getAllBatchHasVideoByBatchId(studentBatchId);
        Video video = videoService.getVideoById(Long.parseLong(videoId));

        studentInfo.setLastWatchVideoId(video.getId());
        usersService.addUser(studentInfo);

        model.addAttribute("video", video);
        model.addAttribute("courseName", courseName);
        model.addAttribute("batchHasVideoList", batchHasVideoList);
        model.addAttribute("batchId", studentBatchId);
        return "/student/STU-VID-06";
    }

}
