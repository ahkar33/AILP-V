package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.AssignmentAnswer;
import com.ace.ailpv.repository.AssignmentAnswerRepository;

@Service
public class AssignmentAnswerService {
    @Autowired
    AssignmentAnswerRepository assignmentAnswerRepository;

    public void addStudentAnswer(AssignmentAnswer answer) {
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

    
}
