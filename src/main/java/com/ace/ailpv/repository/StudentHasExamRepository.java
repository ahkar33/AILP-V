package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.StudentHasExam;

public interface StudentHasExamRepository extends JpaRepository<StudentHasExam, Long> {
   
    StudentHasExam findBySheStudent_IdAndSheExam_Id(String studentId, Long examId);

}
