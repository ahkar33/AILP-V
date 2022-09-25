package com.ace.ailpv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.FileExamResult;
import com.ace.ailpv.repository.FileExamResultRepository;

@Service
public class FileExamResultService {

    @Autowired
    private FileExamResultRepository fileExamResultRepository;

    public void addFileExamResult(FileExamResult fileExamResult) {
        fileExamResultRepository.save(fileExamResult);
    }

}
