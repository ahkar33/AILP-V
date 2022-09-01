package com.ace.ailpv.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ailpv.entity.Assignment;
import com.ace.ailpv.entity.AssignmentFile;
import com.ace.ailpv.repository.AssignmentRepository;

@Service
public class AssignmentService {
    
    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    AssignmentFileService assignmentFileService;

    @Autowired
    FileService fileService;

    public List<Assignment> getAllAssignment(){
        return assignmentRepository.findAll();
    }

    public void addAssignment(Assignment assignment) throws IllegalStateException, IOException {

        fileService.createFolderForAssignment(assignment.getName().trim());

        assignmentRepository.save(assignment);
        Assignment resAssignment = getAssignmentById(assignment.getId());
        for (MultipartFile assignmentFile : assignment.getAssignmentFile()) {
            fileService.createFile(assignmentFile, assignment.getName().trim()+ "\\assignment");
            AssignmentFile asgmFile = new AssignmentFile();
            asgmFile.setName(assignmentFile.getOriginalFilename());
            asgmFile.setAssignment(resAssignment);
            assignmentFileService.addAssignmentFile(asgmFile);
        }
        
    }

    public Assignment getAssignmentById(Long id){
        return assignmentRepository.findById(id).get();
    }

    public List<Assignment> getAllAssignmentByBatchId(Long batchId){
        List<Assignment> unfilterList = assignmentRepository.findByBatch_Id(batchId);
        Long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        List<Assignment> filterList = unfilterList.stream()
                .filter(assignment -> currentTime >= assignment.getStartTime().toEpochSecond(ZoneOffset.UTC))
                .collect(Collectors.toList());
        return filterList;
    }
    
}
