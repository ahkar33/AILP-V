package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.AssignmentResult;
import com.ace.ailpv.repository.AssignmentResultRepository;

@Service
public class AssignmentResultService {
  
    @Autowired
    private AssignmentResultRepository assignmentResultRepository;

    public void addAssignmentResult(AssignmentResult result) {
        assignmentResultRepository.save(result);
    }

    public List<AssignmentResult> getAssignmentResultListByStudentId(String id) {
        return assignmentResultRepository.findByAssignmentResultAnswer_AnswerStudent_Id(id);
    }

}
