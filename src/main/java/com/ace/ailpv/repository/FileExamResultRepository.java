package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.FileExamResult;

public interface FileExamResultRepository extends JpaRepository<FileExamResult, Long>{
   
    List<FileExamResult> findByExamResultAnswer_ExamAnswerStudent_Id(String studentId);

}
