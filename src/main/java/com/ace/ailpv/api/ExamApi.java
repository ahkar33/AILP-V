package com.ace.ailpv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.service.ExamService;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin(origins = "*")
public class ExamApi {

    @Autowired
    private ExamService examService;

    @PostMapping("/addExam")
    public void addExam(@RequestBody Exam exam) {
        examService.addExam(exam);
    }

}
