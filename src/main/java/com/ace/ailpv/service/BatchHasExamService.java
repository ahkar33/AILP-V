package com.ace.ailpv.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public BatchHasExam getBatchHasExamById(Long bheId) {
        return batchHasExamRepository.findById(bheId).orElse(null);
    }

    public BatchHasExam getBatchHasExamByExamIdAndBatchId(Long examId, Long batchId) {
        return batchHasExamRepository.findByBheExam_IdAndBheBatch_Id(examId, batchId);
    }

    public List<BatchHasExam> getBatchHasExamListByBatchId(Long batchId) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mm a");
        List<BatchHasExam> unformattedBheList = batchHasExamRepository.findByBheBatch_Id(batchId);
        List<BatchHasExam> formattedBheList = new ArrayList<>();
        for (BatchHasExam bhe : unformattedBheList) {
            String dateTimeString = bhe.getStartDateTime().format(dateTimeFormatter);
            bhe.setStartTime(dateTimeString);
            formattedBheList.add(bhe);
        }
        return formattedBheList;
    }

}
