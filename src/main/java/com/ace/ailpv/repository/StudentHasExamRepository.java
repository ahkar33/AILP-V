package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.StudentHasExam;

public interface StudentHasExamRepository extends JpaRepository<StudentHasExam, Long> {
   
    StudentHasExam findBySheStudent_IdAndSheExam_Id(String studentId, Long examId);

    List<StudentHasExam> findBySheStudent_Id(String studentId);

}
