package com.ace.ailpv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.BatchHasExam;
import com.ace.ailpv.repository.BatchHasExamRepository;

@Service
public class BatchHasExamService {
  
    @Autowired
    private BatchHasExamRepository batchHasExamRepository;    

    public void addBatchHasExam(BatchHasExam batchHasExam) {
        batchHasExamRepository.save(batchHasExam);
    }

    public BatchHasExam getBatchHasExamByExamIdAndBatchId(Long examId, Long batchId){
        return batchHasExamRepository.findByBheExam_IdAndBheBatch_Id(examId, batchId);
    }

    // public Boolean isExistsByExamIdAndBatchId(Long examId, Long batchId) {
    //     return batchHasExamRepository.existsByBheExam_IdAndBheBatch_Id(examId, batchId);
    // }

}
