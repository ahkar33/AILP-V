package com.ace.ailpv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.repository.ExamRepository;


@Service
public class ExamService {
  
    @Autowired
    private ExamRepository examRepository;    

    public void addExam(Exam exam) {
        examRepository.save(exam);
    }

}
