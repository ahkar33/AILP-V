package com.ace.ailpv.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.BatchHasFileExam;
import com.ace.ailpv.repository.BatchHasFileExamRepository;

@Service
public class BatchHasFileExamService {

    @Autowired
    private BatchHasFileExamRepository batchHasFileExamRepository;

    public void addBatchHasFileExam(BatchHasFileExam batchHasFileExam) {
        batchHasFileExamRepository.save(batchHasFileExam);
    }

    public BatchHasFileExam getBatchHasFileExamById(Long bhfeId) {
        return batchHasFileExamRepository.findById(bhfeId).orElse(null);
    }

    public BatchHasFileExam getBatchHasFileExamByFileExamIdAndBatchId(Long examId, Long batchId) {
        return batchHasFileExamRepository.findByBhfeExam_IdAndBhfeBatch_Id(examId, batchId);
    }

    public List<BatchHasFileExam> getBatchHasFileExamListByBatchId(Long batchId) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mm a");
        List<BatchHasFileExam> unformattedBhfeList = batchHasFileExamRepository.findByBhfeBatch_Id(batchId);
        List<BatchHasFileExam> formattedBhfeList = new ArrayList<>();
        for (BatchHasFileExam bhfe : unformattedBhfeList) {
            String dateTimeString = bhfe.getStartDateTime().format(dateTimeFormatter);
            bhfe.setStartTime(dateTimeString);
            formattedBhfeList.add(bhfe);
        }
        return formattedBhfeList;
    }

    public List<BatchHasFileExam> getBatchHasFileExamListByBatchIdForStudents(Long batchId) {
        Long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        List<BatchHasFileExam> unfilteredList = getBatchHasFileExamListByBatchId(batchId);
        List<BatchHasFileExam> filteredList = unfilteredList.stream()
                .filter(batchHasFileExam -> currentTime > batchHasFileExam.getStartDateTime()
                        .toEpochSecond(ZoneOffset.UTC)
                        && currentTime < batchHasFileExam.getEndDateTime().toEpochSecond(ZoneOffset.UTC))
                .collect(Collectors.toList());
        return filteredList;
    }

}
