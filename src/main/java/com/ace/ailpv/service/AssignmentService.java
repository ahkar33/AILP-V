package com.ace.ailpv.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Assignment;
import com.ace.ailpv.repository.AssignmentRepository;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired 
    private FileUploadUtilService fileUploadUtilService;

    String path = "src\\main\\resources\\static\\courses\\";

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public void addAssignment(Assignment assignment) throws IllegalStateException, IOException {

        String courseName = assignment.getAssignmentBatch().getBatchCourse().getName().trim();
        // fileService.createFolderForAssignment(courseName);
        // fileUploadUtilService.saveFile(path + courseName + "\\" + "assignmentQuestion\\", assignment.getQuestionFileName() , assignment.getQuestionFile());
        System.out.println(assignment.getQuestionFile().getOriginalFilename());
        fileUploadUtilService.saveFile(path + courseName + "\\" + "AssignmentQuestion\\", assignment.getQuestionFile().getOriginalFilename(), assignment.getQuestionFile());

    }

    public Assignment getAssignmentById(Long id){
        return assignmentRepository.findById(id).get();
    }

}
