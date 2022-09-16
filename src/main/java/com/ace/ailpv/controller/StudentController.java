package com.ace.ailpv.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ace.ailpv.entity.Assignment;
import com.ace.ailpv.entity.AssignmentAnswer;
import com.ace.ailpv.entity.AssignmentResult;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.BatchHasExam;
import com.ace.ailpv.entity.BatchHasResource;
import com.ace.ailpv.entity.BatchHasVideo;
import com.ace.ailpv.entity.User;

import com.ace.ailpv.entity.Video;
import com.ace.ailpv.service.AssignmentAnswerService;
import com.ace.ailpv.service.AssignmentResultService;
import com.ace.ailpv.service.AssignmentService;
import com.ace.ailpv.service.BatchHasExamService;
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

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentAnswerService assignmentAnswerService;

    @Autowired
    private AssignmentResultService assignmentResultService;

    @Autowired
    private BatchHasExamService batchHasExamService;

    @GetMapping("/student-home")
    public String showStudentHomePage(HttpSession session, ModelMap model) {
        String studentId = (String) session.getAttribute("uid");
        String batchId = (String) session.getAttribute("batchId");
        model.addAttribute("userId", studentId);
        model.addAttribute("batchId", batchId);
        return "/student/STU-HOM-01";
    }

    @GetMapping("/student-public-chat")
    public String setupStudentPublicChat(HttpSession session, ModelMap model) {
        String studentId = (String) session.getAttribute("uid");
        String batchId = (String) session.getAttribute("batchId");
        String username = usersService.getUserById(studentId).getName();
        String batchName = batchService.getBatchById(Long.parseLong(batchId)).getName();
        model.addAttribute("userId", studentId);
        model.addAttribute("username", username);
        model.addAttribute("batchId", batchId);
        model.addAttribute("batchName", batchName);
        return "/student/STU-PBC-05";
    }

    @GetMapping("/getResources")
    public String getResources(ModelMap model, HttpSession session) {
        String studentBatchId = (String) session.getAttribute("batchId");
        List<BatchHasResource> batchHasResourceList = batchHasResourceService
                .getAllBatchHasResourcesByBatchId(Long.parseLong(studentBatchId));
        model.addAttribute("batchHasResourceList", batchHasResourceList);
        return "/student/STU-REC-03";
    }

    @GetMapping("/getVideos/{batchId}")
    public String getVideos(ModelMap model, HttpSession session, @PathVariable("batchId") String batchId) {
        String userId = (String) session.getAttribute("uid");
        User userInfo = usersService.getUserById(userId);
        Long userBatchId;
        if (batchId.equals("AILP")) {
            String resBatchId = (String) session.getAttribute("batchId");
            userBatchId = Long.parseLong(resBatchId);
        } else {
            userBatchId = Long.parseLong(batchId);
        }

        Batch studentBatch = batchService.getBatchById(userBatchId);
        List<BatchHasVideo> batchHasVideoList = batchHasVideoService
                .getAllBatchHasVideoByBatchId(userBatchId);
        List<User> teacherList = usersService.getTeacherListByBatchId(userBatchId);

        if (userInfo.getLastWatchVideoId() != null) {
            Video video = videoService.getVideoById(userInfo.getLastWatchVideoId());
            if (video != null) {
                model.addAttribute("video", video);
            }
        } else {
            List<BatchHasVideo> batchVideoList = batchHasVideoService.getAllBatchHasVideoByBatchId(userBatchId);
            BatchHasVideo batchHasVideo = new BatchHasVideo();
            if (batchVideoList.size() > 0) {
                batchHasVideo = batchHasVideoService.getAllBatchHasVideoByBatchId(userBatchId).get(0);
                model.addAttribute("video", batchHasVideo.getVideo());
            }
            else {
                Video video = new Video();
                video.setId(null);
                batchHasVideo.setVideo(video);
                model.addAttribute("video", batchHasVideo.getVideo());
            }
        }
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("courseName", studentBatch.getBatchCourse().getName());
        model.addAttribute("batchHasVideoList", batchHasVideoList);
        model.addAttribute("batchId", userBatchId);
        model.addAttribute("username", userInfo.getName());
        return "/student/STU-VID-02";
    }

    @GetMapping("/showClickedVideo/{courseName}/{videoId}/{batchId}")
    public String showClickedVideo(HttpSession session, ModelMap model, @PathVariable("videoId") String videoId,
            @PathVariable("courseName") String courseName, @PathVariable("batchId") String batchId) {
        String userId = (String) session.getAttribute("uid");
        User userInfo = usersService.getUserById(userId);

        long userBatchId;
        if (batchId.equals("AILP")) {
            String resBatchId = (String) session.getAttribute("batchId");
            userBatchId = Long.parseLong(resBatchId);
        } else {
            userBatchId = Long.parseLong(batchId);
        }

        List<BatchHasVideo> batchHasVideoList = batchHasVideoService
                .getAllBatchHasVideoByBatchId(userBatchId);
        List<User> teacherList = usersService.getTeacherListByBatchId(userBatchId);

        Video video = videoService.getVideoById(Long.parseLong(videoId));

        if (userInfo.getRole().equals("ROLE_STUDENT")) {
            userInfo.setLastWatchVideoId(video.getId());
            usersService.addUser(userInfo);
        }

        model.addAttribute("teacherList", teacherList);
        model.addAttribute("video", video);
        model.addAttribute("courseName", courseName);
        model.addAttribute("batchHasVideoList", batchHasVideoList);
        model.addAttribute("batchId", userBatchId);
        model.addAttribute("username", userInfo.getName());
        return "/student/STU-VID-02";

    }

    @GetMapping("/studentAssignment")
    public String studentAssignment(ModelMap model, HttpSession session) {
        String studentBatchId = (String) session.getAttribute("batchId");
        List<Assignment> assignmentList = assignmentService.getAllAssignmentByBatchId(Long.parseLong(studentBatchId));
        model.addAttribute("assignmentList", assignmentList);
        model.addAttribute("answer", new AssignmentAnswer());
        return "/student/STU-ASG-04";
    }

    @PostMapping("/submitAssignment")
    public String submitAssignment(@ModelAttribute("answer") AssignmentAnswer answer,RedirectAttributes redirectAttr) throws IOException {
        Long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        Assignment assignment = assignmentService.getAssignmentById(answer.getAssignment().getId());
        Long startTime = assignment.getEndTime().toEpochSecond(ZoneOffset.UTC);
        if (currentTime > startTime) {
            answer.setIsLate(true);
        }
        answer.setAnswerFileName(answer.getAnswerFile().getOriginalFilename());
        assignmentAnswerService.addAssignmentAnswer(answer);
         redirectAttr.addFlashAttribute("successMsg", true);
        return "redirect:/student/studentAssignment";
    }

    @GetMapping("/studentGradeBook")
    public String showGradeBook(ModelMap model, HttpSession session) {
        String studentId = (String) session.getAttribute("uid");
        List<AssignmentResult> resultList = assignmentResultService.getAssignmentResultListByStudentId(studentId);
        model.addAttribute("resultList", resultList);
        return "/student/STU-GRB-08";
    }

    @GetMapping("/getExamList")
    public String getBheListByBatchId(HttpSession session, ModelMap model) {
        String studentBatchId = (String) session.getAttribute("batchId");
        List<BatchHasExam> bheList = batchHasExamService.getBatchHasExamListByBatchId(Long.parseLong(studentBatchId));
        model.addAttribute("bheList", bheList);
        return "/student/STU-EXL-06";
    }

    @GetMapping("/getExam/{bheId}")
    public String getExam(@PathVariable("bheId") Long bheId, ModelMap model, HttpSession session) {
        String studentId = (String) session.getAttribute("uid");
        // User studentInfo = usersService.getUserById(studentId);
        BatchHasExam batchHasExam = batchHasExamService.getBatchHasExamById(bheId);
        model.addAttribute("bheId", bheId);
        model.addAttribute("studentId", studentId);
        model.addAttribute("examId", batchHasExam.getBheExam().getId());
        return "/student/STU-EXM-07";
    }

}
