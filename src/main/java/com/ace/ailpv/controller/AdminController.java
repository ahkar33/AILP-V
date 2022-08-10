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

import com.ace.ailpv.entity.Course;
import com.ace.ailpv.service.CourseService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/course-table")
    public String setupCourseTable(ModelMap model) {
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("course", new Course());
        return "/admin/adm-cou-04";
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

    @GetMapping("/batch-table")
    public String setupBatchTable() {
        return "/admin/batch-table";
    }

    @GetMapping("/exam-table")
    public String setupExamTable() {
        return "/admin/adm-etb-05";
    }

    @GetMapping("/create-exam")
    public String setupCreateExam() {
        return "/admin/adm-cre-06";
    }

}