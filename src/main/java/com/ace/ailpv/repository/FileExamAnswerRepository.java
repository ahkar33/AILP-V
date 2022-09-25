package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.FileExamAnswer;

public interface FileExamAnswerRepository extends JpaRepository<FileExamAnswer, Long>{

    Boolean existsByExamAnswerStudent_IdAndBatchHasFileExam_Id(String studentId, Long bhfeId);    

    FileExamAnswer findByExamAnswerStudent_IdAndBatchHasFileExam_Id(String studentId, Long bhfeId);

    List<FileExamAnswer> findByBatchHasFileExam_Id(Long id);

}
