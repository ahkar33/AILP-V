package com.ace.ailpv.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.FileExamAnswer;
import com.ace.ailpv.repository.FileExamAnswerRepository;

@Service
public class FileExamAnswerService {

    @Autowired
    private FileExamAnswerRepository fileExamAnswerRepository;

    @Autowired
    private FileUploadUtilService fileUploadUtilService;

    @Autowired
    private FileService fileService;

    String path = "src\\main\\resources\\static\\courses\\";

    public void addFileExamAnswer(FileExamAnswer fileExamAnswer, Long batchId) throws IOException {
        String answerFileName = fileExamAnswer.getExamAnswerFile().getOriginalFilename();
        String studentId = fileExamAnswer.getExamAnswerStudent().getId();
        String courseName = fileExamAnswer.getBatchHasFileExam().getBhfeExam().getFileExamCourse().getName();
        Long bhfeId = fileExamAnswer.getBatchHasFileExam().getId();
        if (!fileExamAnswerRepository.existsByExamAnswerStudent_IdAndBatchHasFileExam_Id(studentId,
                bhfeId)) {
            fileUploadUtilService.saveFile(path + courseName + "\\" + Long.toString(batchId) + "\\" + "ExamAnswer",
                    studentId + answerFileName, fileExamAnswer.getExamAnswerFile());
            fileExamAnswer.setExamAnswerFileName(studentId + answerFileName);
            fileExamAnswerRepository.save(fileExamAnswer);
        } else {
            FileExamAnswer resAnswer = fileExamAnswerRepository.findByExamAnswerStudent_IdAndBatchHasFileExam_Id(studentId,
                    bhfeId);
            fileService.deleteFile(path + courseName + "\\" + Long.toString(batchId) + "\\" + "ExamAnswer" + "\\" +
                    resAnswer.getExamAnswerFileName());
            fileUploadUtilService.saveFile(path + courseName + "\\" + Long.toString(batchId) + "\\" + "ExamAnswer",
                    studentId + answerFileName, fileExamAnswer.getExamAnswerFile());
            resAnswer.setExamAnswerFileName(studentId + answerFileName);
            fileExamAnswerRepository.save(resAnswer);
        }
    }

    public List<FileExamAnswer> getFileExamAnswerListByBhfeId(Long id) {
        return fileExamAnswerRepository.findByBatchHasFileExam_Id(id); 
    }

}
