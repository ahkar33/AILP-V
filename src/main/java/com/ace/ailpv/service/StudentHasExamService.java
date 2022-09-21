package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.StudentHasExam;
import com.ace.ailpv.repository.StudentHasExamRepository;

@Service
public class StudentHasExamService {

    @Autowired
    private StudentHasExamRepository studentHasExamRepository;

    public void addStudentHasExam(StudentHasExam studentHasExam) {
        studentHasExamRepository.save(studentHasExam);
    }

    public StudentHasExam getStudentHasExamByStudentIdAndExamId(String studentId, Long examId) {
        return studentHasExamRepository.findBySheStudent_IdAndSheExam_Id(studentId, examId);
    }

    public List<StudentHasExam> getStudentHasExamListByStudentId(String studentId) {
        return studentHasExamRepository.findBySheStudent_Id(studentId);
    }
}
