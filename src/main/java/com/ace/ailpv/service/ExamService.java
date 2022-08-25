package com.ace.ailpv.service;

import java.util.List;

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

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id).get();
    }

    public void deleteExamById(Long id) {
        examRepository.deleteById(id);
    }

    public List<Exam> getExamListByBatchId(Long id) {
        return examRepository.findByExamCourse_Id(id);
    }

}
