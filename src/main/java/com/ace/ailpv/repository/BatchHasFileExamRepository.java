package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.BatchHasFileExam;

public interface BatchHasFileExamRepository extends JpaRepository<BatchHasFileExam, Long>{

    BatchHasFileExam findByBhfeExam_IdAndBhfeBatch_Id(Long examId,Long batchId);

    List<BatchHasFileExam> findByBhfeBatch_Id(Long batchId);

}
