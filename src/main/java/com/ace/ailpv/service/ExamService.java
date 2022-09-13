package com.ace.ailpv.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.ExamRepository;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserService userService;

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

    public List<Exam> getExamListByCourseId(Long id) {
        return examRepository.findByExamCourse_Id(id);
    }

    public List<Exam> getExamListByTeacherId(String teacherId) {
        User teacherInfo = userService.getUserById(teacherId);
        List<Exam> examList = new ArrayList<>();
        Set<Long> courseIdList = new HashSet<>();
        for (Batch batch : teacherInfo.getBatchList()) {
            courseIdList.add(batch.getBatchCourse().getId());
        }
        for(Long courseId : courseIdList) {
            examList.addAll(getExamListByCourseId(courseId));
        }
        return examList;
    }

}
