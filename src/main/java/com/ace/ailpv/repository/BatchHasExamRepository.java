package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.BatchHasExam;

public interface BatchHasExamRepository extends JpaRepository<BatchHasExam, Long> {

    // Boolean existsByBheExam_IdAndBheBatch_Id(Long examId, Long batchId);

    BatchHasExam findByBheExam_IdAndBheBatch_Id(Long examId, Long batchId);

    List<BatchHasExam> findByBheBatch_Id(Long batchId);
    
}
