package com.ace.ailpv.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ailpv.entity.AssignmentAnswer;
import com.ace.ailpv.repository.AssignmentAnswerRepository;

@Service
public class AssignmentAnswerService {
    @Autowired
    AssignmentAnswerRepository assignmentAnswerRepository;

    @Autowired
    FileService fileService;

    public void addStudentAnswer(AssignmentAnswer answer, MultipartFile answerFile)throws IOException {
        String questionFileName = answer.getAssignment().getName();
        fileService.createFolderForAssignmentAnswer(questionFileName);
        fileService.createFile(answerFile, questionFileName+ "\\assignment"+"\\answer");
        assignmentAnswerRepository.save(answer);
    }

    public List<AssignmentAnswer> getAssignmentAnswerByAssignmentId(Long assignmentId){
        return assignmentAnswerRepository.getAnswerByAssignmentId(assignmentId);
    }

    public String getAsssignmentNameByAssignmentId(Long assignmentId){
        return assignmentAnswerRepository.getAssignmentNameByAssignmentId(assignmentId);
    }

    public AssignmentAnswer getAssignmentAnswerById(Long asgmAnswerId) {
        return assignmentAnswerRepository.getById(asgmAnswerId);
    }

    public void addTeacherResponse(AssignmentAnswer asgmAnswer) {
        assignmentAnswerRepository.save(asgmAnswer);
    }   
}
