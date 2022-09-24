package com.ace.ailpv.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.FileExam;
import com.ace.ailpv.repository.FileExamRepository;

@Service
public class FileExamService {

    @Autowired
    private FileExamRepository examFileRepository;

    @Autowired
    private FileUploadUtilService fileUploadUtilService;

    String path = "src\\main\\resources\\static\\courses\\";

    public Boolean checkFileExamName(String name, Long courseId) {
        return examFileRepository.existsByNameAndFileExamCourse_Id(name, courseId);
    }

    public void addFileExam(FileExam fileExam) throws IOException {
        String fileName = fileExam.getQuestionFile().getOriginalFilename();
        fileExam.setQuestionFileName(fileName);
        String courseName = fileExam.getFileExamCourse().getName().trim();
        fileUploadUtilService.saveFile(path + courseName + "\\exam\\", fileName, fileExam.getQuestionFile());
        examFileRepository.save(fileExam);
    }

    public List<FileExam> getAllFileExams() {
        return examFileRepository.findAll();
    }

    public void deleteFileExamById(Long id) {
        examFileRepository.deleteById(id);
    }

}
