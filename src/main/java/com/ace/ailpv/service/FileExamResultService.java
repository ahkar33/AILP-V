package com.ace.ailpv.service;

import java.util.List;

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

    public List<FileExamResult> getFileExamResultListByStudentId(String id) {
        return fileExamResultRepository.findByExamResultAnswer_ExamAnswerStudent_Id(id);
    }

}
