package com.ace.ailpv.service;

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
    
}
