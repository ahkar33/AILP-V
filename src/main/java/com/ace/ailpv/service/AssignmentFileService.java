package com.ace.ailpv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.AssignmentFile;
import com.ace.ailpv.repository.AssignmentFileRepository;

@Service
public class AssignmentFileService {

    @Autowired
    AssignmentFileRepository assignmentFileRepository;

    public void addAssignmentFile(AssignmentFile asgmFile) {
        assignmentFileRepository.save(asgmFile);
    }
    
}
