package com.ace.ailpv.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.Course;
import com.ace.ailpv.service.CourseService;

@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins = "*")
public class CourseApi {

    @Autowired
    private CourseService courseService;

    @GetMapping("/getAllCourses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

}