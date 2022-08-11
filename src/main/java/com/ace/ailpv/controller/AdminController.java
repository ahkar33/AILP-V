package com.ace.ailpv.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.CourseService;
import com.ace.ailpv.service.ExamService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private BatchService batchService;
    
    @Autowired 
    private ExamService examService;


    @GetMapping("/course-table")
    public String setupCourseTable(ModelMap model) {
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("course", new Course());
        return "/admin/ADM-COU-04";
    }

    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("course") Course course, RedirectAttributes redirectAttrs)
            throws IllegalStateException, IOException {
        if (courseService.checkCourseName(course.getName())) {
            redirectAttrs.addFlashAttribute("msg", "course name already exists");
            return "redirect:/admin/course-table";
        }
        courseService.addCourse(course);
        return "redirect:/admin/course-table";
    }

    @GetMapping("/deleteCourse/{id}/{courseName}")
    public String deleteCourse(@PathVariable("id") Long id, @PathVariable("courseName") String courseName)
            throws IOException {
        courseService.deleteCourseById(id, courseName);
        return "redirect:/admin/course-table";
    }

    @GetMapping("/exam-table")
    public String setupExamTable(ModelMap model) {
        model.addAttribute("examList", examService.getAllExams());
        return "/admin/ADM-ETB-05";
    }

    @GetMapping("/create-exam")
    public String setupCreateExam(ModelMap model) {
        model.addAttribute("courseList", courseService.getAllCourses());
        return "/admin/ADM-CRE-06";
    }

    @GetMapping("/batch-table")
    public String setupBatchTable(ModelMap model) {
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("batch", new Batch());
        model.addAttribute("editBatch", new Batch());
        model.addAttribute("batchList", batchService.getAllBatches());
        return "/admin/ADM-BTB-05";
    }

    @PostMapping("/addBatch")
    public String addBatch(@ModelAttribute("batch") Batch batch) {
        batch.setBatchCourse(courseService.getCourseById(batch.getCourseId()));
        batchService.addBatch(batch);
        return "redirect:/admin/batch-table";
    }

    @GetMapping("/deleteBatch/{id}")
    public String deleteBatch(@PathVariable("id") Long id) {
        batchService.deleteBatchById(id);
        return "redirect:/admin/batch-table";
    }

    @GetMapping("/editBatch/{id}")
    public String editBatch(@PathVariable("id") Long id) {
        return "/admin/ADM-EDB-11";
    }

    @GetMapping("/student-table")
    public String setupStudentTable() {
        return "/admin/ADM-STB-08";
    }

    @GetMapping("/studentRegister")
    public String setupStudentRegister(ModelMap model) {
        model.addAttribute("batchList", batchService.getAllBatches());
        return "/admin/ADM-STG-07";
    }

    @GetMapping("/deleteExam/{id}")
    public String deleteExam(@PathVariable("id") Long id)
            throws IOException {
        examService.deleteExamById(id);
        return "redirect:/admin/exam-table";

    }

}