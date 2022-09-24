package com.ace.ailpv.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.FileExam;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.FileExamRepository;

@Service
public class FileExamService {

    @Autowired
    private FileExamRepository examFileRepository;

    @Autowired
    private FileUploadUtilService fileUploadUtilService;

    @Autowired
    private UserService userService;

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

    public FileExam getFileExamById(Long id) {
        return examFileRepository.findById(id).orElse(null);
    }

    public List<FileExam> getFileExamListByCourseId(Long courseId) {
        return examFileRepository.findByFileExamCourse_Id(courseId);
    }

    public List<FileExam> getFileExamListByTeacherId(String teacherId) {
        User teacherInfo = userService.getUserById(teacherId);
        List<FileExam> fileExamList = new ArrayList<>();
        Set<Long> courseIdList = new HashSet<>();
        for (Batch batch : teacherInfo.getBatchList()) {
            courseIdList.add(batch.getBatchCourse().getId());
        }
        for(Long courseId : courseIdList) {
            fileExamList.addAll(getFileExamListByCourseId(courseId));
        }
        return fileExamList;
    }

}
