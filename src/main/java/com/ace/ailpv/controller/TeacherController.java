package com.ace.ailpv.controller;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
import com.ace.ailpv.entity.BatchHasFileExam;
import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.entity.FileExam;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.UserSchedule;
import com.ace.ailpv.security.BatchHasFileExamService;
import com.ace.ailpv.service.AssignmentAnswerService;
import com.ace.ailpv.service.AssignmentResultService;
import com.ace.ailpv.service.AssignmentService;
import com.ace.ailpv.service.BatchHasExamService;
import com.ace.ailpv.service.ExamService;
import com.ace.ailpv.service.FileExamService;
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
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentAnswerService assignmentAnswerService;

    @Autowired
    private AssignmentResultService assignmentResultService;

    @Autowired
    private ExamService examService;

    @Autowired
    private FileExamService fileExamService;

    @Autowired
    private BatchHasExamService batchHasExamService;

    @Autowired
    private BatchHasFileExamService batchHasFileExamService;

    @GetMapping("/dashboard")
    public String setupTeacherDashboard(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        // batchList = batchList.stream()
        // .filter(batch -> batch.getIsActive())
        // .collect(Collectors.toList());
        model.addAttribute("batchList", batchList);
        model.addAttribute("teacherId", teacherId);
        return "/teacher/TCH-DSB-01";
    }

    // @GetMapping("/student-table")
    // public String setupStudentTable(ModelMap model, HttpSession session) {
    // String teacherId = (String) session.getAttribute("uid");
    // User teacherInfo = userService.getUserById(teacherId);
    // model.addAttribute("studentList",
    // userService.getStudentListByTeacherId(teacherInfo.getId()));
    // return "/teacher/TCH-STB-11";
    // }

    @GetMapping("/teacher-public-chat")
    public String setupTeacherPublicChat(HttpSession session, ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        User teacherInfo = userService.getUserById(teacherId);
        List<Batch> batchList = userService.getTeacherBatchListById(teacherInfo.getId());
        // batchList = batchList.stream().filter(batch ->
        // batch.getIsActive()).collect(Collectors.toList());
        Batch firstBatch = batchList.get(0);
        model.addAttribute("userId", teacherInfo.getId());
        model.addAttribute("username", teacherInfo.getName());
        model.addAttribute("batchId", firstBatch.getId());
        model.addAttribute("batchName", firstBatch.getName());
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-PBC-04";
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
        // batchList = batchList.stream().filter(batch ->
        // batch.getIsActive()).collect(Collectors.toList());
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-PBC-04";
    }

    @GetMapping("/modifyAttendance")
    public String setupModifyAttendance(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-MDA-12";
    }

    @GetMapping("/attendance-table")
    public String setupAttendanceTable(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        String batchId = (String) session.getAttribute("batchId");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        List<UserSchedule> res = userScheduleService.getUserScheduleListByBatchId(Long.parseLong(batchId));
        List<String> dateList = new ArrayList<>();
        for (UserSchedule u : res) {
            String date = u.getSchedule().getDate().toString();
            if (!dateList.contains(date)) {
                dateList.add(date);
            }
        }
        List<User> studentList = userService.getStudentListByBatchId(Long.parseLong(batchId));
        model.addAttribute("batchList", batchList);
        model.addAttribute("dateList", dateList);
        model.addAttribute("studentList", studentList);
        model.addAttribute("batch", new Batch());
        return "/teacher/TCH-ATB-11";
    }

    @PostMapping("/searchScheduleForBatch")
    public String searchScheduleForBatch(@ModelAttribute("batch") Batch batch, HttpSession session, ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        List<UserSchedule> res = userScheduleService.getUserScheduleListByBatchId(batch.getId());
        List<String> dateList = new ArrayList<>();
        for (UserSchedule u : res) {
            String date = u.getSchedule().getDate().toString();
            if (!dateList.contains(date)) {
                dateList.add(date);
            }
        }
        List<User> studentList = userService.getStudentListByBatchId(batch.getId());
        model.addAttribute("batchList", batchList);
        model.addAttribute("dateList", dateList);
        model.addAttribute("studentList", studentList);
        model.addAttribute("batch", batch);
        return "/teacher/TCH-ATB-11";
    }

    // @PostMapping("/searchUserScheduleList")
    // public String searchUserScheduleList(@ModelAttribute("data") UserSchedule
    // userSchedule, ModelMap model,
    // HttpSession session) {
    // Long batchId = userSchedule.getUser().getBatchList().get(0).getId();
    // if (userSchedule.getDate() != null) {
    // Schedule resSchedule =
    // scheduleService.getScheduleByDate(userSchedule.getDate());
    // if (resSchedule == null) {
    // String teacherId = (String) session.getAttribute("uid");
    // List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
    // model.addAttribute("userScheduleList", new ArrayList<>());
    // model.addAttribute("batchList", batchList);
    // model.addAttribute("data", new UserSchedule());
    // return "/teacher/TCH-ATB-11";
    // }
    // List<UserSchedule> list =
    // userScheduleService.getUserScheduleListByBatchIdAndScheduleId(batchId,
    // resSchedule.getId());
    // String teacherId = (String) session.getAttribute("uid");
    // List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
    // model.addAttribute("userScheduleList", list);
    // model.addAttribute("batchList", batchList);
    // model.addAttribute("data", new UserSchedule());
    // return "/teacher/TCH-ATB-11";
    // } else {
    // List<UserSchedule> list =
    // userScheduleService.getUserScheduleListByBatchIdOrScheduleId(batchId);
    // String teacherId = (String) session.getAttribute("uid");
    // List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
    // model.addAttribute("userScheduleList", list);
    // model.addAttribute("batchList", batchList);
    // model.addAttribute("data", userSchedule);
    // return "/teacher/TCH-ATB-11";
    // }
    // }

    @GetMapping("/postResource")
    public String setupPostResource(HttpSession session, ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-UPR-02";
    }

    @GetMapping("/postVideo")
    public String setupPostVideo(HttpSession session, ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-UPV-03";
    }

    @GetMapping("/assignment-table")
    public String setupAssignmentTable(HttpSession session, ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        model.addAttribute("assignment", new Assignment());
        model.addAttribute("batchList", batchList);
        List<Assignment> assignmentList = new ArrayList<>();
        for (Batch batch : batchList) {
            assignmentList.addAll(assignmentService.getAllAssignmentByBatchId(batch.getId()));
        }
        model.addAttribute("assignmentList", assignmentList);
        return "/teacher/TCH-AST-05";
    }

    @PostMapping("/createAssignment")
    public String createAssignment(@ModelAttribute("assignment") Assignment assignment)
            throws IllegalStateException, IOException {
        assignmentService.addAssignment(assignment);
        return "redirect:/teacher/assignment-table";
    }

    @GetMapping("/checkAssignment/{assignmentId}")
    public String checkAssignment(@PathVariable("assignmentId") Long assignmentId, ModelMap model) {
        List<AssignmentAnswer> answerList = assignmentAnswerService.getAssignmentAnswerListByAssignmentId(assignmentId);
        model.addAttribute("answerList", answerList);
        model.addAttribute("result", new AssignmentResult());
        return "/teacher/TCH-ASD-06";
    }

    @PostMapping("/giveAssignmentResult")
    public String giveAssignmentResult(@ModelAttribute("result") AssignmentResult result) {
        AssignmentResult resResult = assignmentResultService
                .getAssignmentResultByAnswerId(result.getAssignmentResultAnswer().getId());
        AssignmentAnswer answer = assignmentAnswerService
                .getAssignmentAnswerById(result.getAssignmentResultAnswer().getId());
        answer.setIsGraded(true);
        assignmentAnswerService.saveAssignmentAnswer(answer);
        if (resResult != null) {
            resResult.setComment(result.getComment());
            resResult.setMark(result.getMark());
            assignmentResultService.addAssignmentResult(resResult);
        } else {
            assignmentResultService.addAssignmentResult(result);
        }
        return "redirect:/teacher/checkAssignment/" + result.getAssignmentResultAnswer().getAssignment().getId();
    }

    @GetMapping("/assignment-grade")
    public String showStudentTable(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        // User teacherInfo = userService.getUserById(teacherId);
        // Long batchId = teacherInfo.getBatchList().get(0).getId();
        String resBatchId = (String) session.getAttribute("batchId");
        Long batchId = Long.parseLong(resBatchId);
        List<Assignment> assignmentList = assignmentService.getAllAssignmentByBatchId(batchId);

        List<User> studentList = userService.getStudentListByBatchId(batchId);

        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);

        model.addAttribute("data", new Batch());
        model.addAttribute("assignmentList", assignmentList);
        model.addAttribute("studentList", studentList);
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-ASG-07";
    }

    @PostMapping("/searchStudentsByBatch")
    public String searchStudentsByBatch(@ModelAttribute("data") Batch batch, ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        Long batchId = batch.getId();
        List<Assignment> assignmentList = assignmentService.getAllAssignmentByBatchId(batchId);
        List<User> studentList = userService.getStudentListByBatchId(batchId);
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        model.addAttribute("data", batch);
        model.addAttribute("assignmentList", assignmentList);
        model.addAttribute("studentList", studentList);
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-ASG-07";
    }

    @GetMapping("/exam-table")
    public String setupUploadExam(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        List<Exam> examList = examService.getExamListByTeacherId(teacherId);
        List<FileExam> fileExamList = fileExamService.getFileExamListByTeacherId(teacherId);
        model.addAttribute("examList", examList);
        model.addAttribute("fileExamList", fileExamList);
        return "/teacher/TCH-ETB-08";
    }

    @GetMapping("/uploadExam/{examId}")
    public String setupUploadExam(@PathVariable("examId") Long examId, HttpSession session, ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        Exam exam = examService.getExamById(examId);
        Long courseId = exam.getExamCourse().getId();
        List<Batch> batchList = userService.getTeacherBatchListByTeacherIdAndCourseId(teacherId, courseId);
        model.addAttribute("exam", exam);
        model.addAttribute("batchList", batchList);
        model.addAttribute("data", new BatchHasExam());
        return "/teacher/TCH-UPE-09";
    }

    @GetMapping("/uploadFileExam/{fileExamId}")
    public String setupFileUploadExam(@PathVariable("fileExamId") Long fileExamId, HttpSession session,
            ModelMap model) {
        String teacherId = (String) session.getAttribute("uid");
        FileExam fileExam = fileExamService.getFileExamById(fileExamId);
        Long courseId = fileExam.getFileExamCourse().getId();
        List<Batch> batchList = userService.getTeacherBatchListByTeacherIdAndCourseId(teacherId, courseId);
        model.addAttribute("exam", fileExam);
        model.addAttribute("batchList", batchList);
        model.addAttribute("data", new BatchHasFileExam());
        return "/teacher/TCH-UFE-14";
    }

    @PostMapping("/uploadExam")
    public String uploadExam(@ModelAttribute("data") BatchHasExam batchHasExam, RedirectAttributes redirectAttrs) {
        Long examId = batchHasExam.getBheExam().getId();
        Long batchId = batchHasExam.getBheBatch().getId();
        BatchHasExam bhe = batchHasExamService.getBatchHasExamByExamIdAndBatchId(examId, batchId);
        Long startTime = batchHasExam.getStartDateTime().toEpochSecond(ZoneOffset.UTC);
        Long endTime = batchHasExam.getEndDateTime().toEpochSecond(ZoneOffset.UTC);
        Long totalTime = endTime - startTime;
        Long hour = totalTime / 3600;
        Long hourMin = (totalTime % 3600) / 60;
        Long min = totalTime / 60;
        String totalTimeStr = "";
        if (hour == 0) {
            totalTimeStr = min + " min";
        } else {
            totalTimeStr = hour + " hr " + hourMin + "min";
        }
        if (bhe != null) {
            bhe.setStartDateTime(batchHasExam.getStartDateTime());
            bhe.setEndDateTime(batchHasExam.getEndDateTime());
            bhe.setTotalTime(totalTimeStr);
            batchHasExamService.addBatchHasExam(bhe);
        } else {
            batchHasExam.setTotalTime(totalTimeStr);
            batchHasExamService.addBatchHasExam(batchHasExam);
        }
        redirectAttrs.addFlashAttribute("isSuccess", true);
        return "redirect:/teacher/uploadExam/" + batchHasExam.getBheExam().getId();
    }

    @PostMapping("/uploadFileExam")
    public String uploadFileExam(@ModelAttribute("data") BatchHasFileExam batchHasFileExam, RedirectAttributes redirectAttrs) {
        Long examId = batchHasFileExam.getBhfeExam().getId();
        Long batchId = batchHasFileExam.getBhfeBatch().getId();
        BatchHasFileExam bhfe = batchHasFileExamService.getBatchHasFileExamByFileExamIdAndBatchId(examId, batchId);
        Long startTime = batchHasFileExam.getStartDateTime().toEpochSecond(ZoneOffset.UTC);
        Long endTime = batchHasFileExam.getEndDateTime().toEpochSecond(ZoneOffset.UTC);
        Long totalTime = endTime - startTime;
        Long hour = totalTime / 3600;
        Long hourMin = (totalTime % 3600) / 60;
        Long min = totalTime / 60;
        String totalTimeStr = "";
        if (hour == 0) {
            totalTimeStr = min + " min";
        } else {
            totalTimeStr = hour + " hr " + hourMin + "min";
        }
        if (bhfe != null) {
            bhfe.setStartDateTime(batchHasFileExam.getStartDateTime());
            bhfe.setEndDateTime(batchHasFileExam.getEndDateTime());
            bhfe.setTotalTime(totalTimeStr);
            batchHasFileExamService.addBatchHasFileExam(bhfe);
        } else {
            batchHasFileExam.setTotalTime(totalTimeStr);
            batchHasFileExamService.addBatchHasFileExam(batchHasFileExam);
        }
        redirectAttrs.addFlashAttribute("isSuccess", true);
        return "redirect:/teacher/uploadFileExam/" + batchHasFileExam.getBhfeExam().getId();
    }

    @GetMapping("/exam-grade")
    public String setupExamGrade(ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        // User teacherInfo = userService.getUserById(teacherId);
        // Long batchId = teacherInfo.getBatchList().get(0).getId();
        String resBatchId = (String) session.getAttribute("batchId");
        Long batchId = Long.parseLong(resBatchId);
        List<BatchHasExam> bheList = batchHasExamService.getBatchHasExamListByBatchId(batchId);
        List<User> studentList = userService.getStudentListByBatchId(batchId);
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        model.addAttribute("data", new Batch());
        model.addAttribute("bheList", bheList);
        model.addAttribute("studentList", studentList);
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-EXG-10";
    }

    @PostMapping("/searchStudentExamsByBatch")
    public String searchStudentExamsByBatch(@ModelAttribute("data") Batch batch, ModelMap model, HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        Long batchId = batch.getId();
        List<BatchHasExam> bheList = batchHasExamService.getBatchHasExamListByBatchId(batchId);
        List<User> studentList = userService.getStudentListByBatchId(batchId);
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        model.addAttribute("data", batch);
        model.addAttribute("bheList", bheList);
        model.addAttribute("studentList", studentList);
        model.addAttribute("batchList", batchList);
        return "/teacher/TCH-EXG-10";
    }

}
