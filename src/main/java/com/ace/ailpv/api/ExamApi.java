package com.ace.ailpv.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.BatchHasExam;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.service.BatchHasExamService;
import com.ace.ailpv.service.CourseService;
import com.ace.ailpv.service.ExamService;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin(origins = "*")
public class ExamApi {

    @Autowired
    private ExamService examService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private BatchHasExamService batchHasExamService;

    @PostMapping("/addExam")
    public void addExam(@RequestBody Exam exam) {
        Long courseId = Long.parseLong(exam.getCourseId());
        Course course = courseService.getCourseById(courseId);
        exam.setExamCourse(course);
        examService.addExam(exam);
    }

    @GetMapping("/getBheById/{id}")
    public BatchHasExam getBheById(@PathVariable("id") Long id) {
        return batchHasExamService.getBatchHasExamById(id);
    }

    // @GetMapping("/getBheListByBatchId/{batchId}")
    // public List<BatchHasExam> getBheListByBatchId(@PathVariable("batchId") Long
    // batchId) {
    // return batchHasExamService.getBatchHasExamListByBatchId(batchId);
    // }

}