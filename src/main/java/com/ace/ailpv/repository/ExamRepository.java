package com.ace.ailpv.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    // List<Exam> getAllExams();
    // void deleteExamById(Long id);

    List<Exam> findByExamCourse_Id(Long id);

}