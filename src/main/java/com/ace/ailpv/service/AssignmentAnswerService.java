package com.ace.ailpv.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.AssignmentAnswer;
import com.ace.ailpv.repository.AssignmentAnswerRepository;

@Service
public class AssignmentAnswerService {

    @Autowired
    private AssignmentAnswerRepository assignmentAnswerRepository;

    @Autowired
    private FileUploadUtilService fileUploadUtilService;

    @Autowired
    private FileService fileService;

    String path = "src\\main\\resources\\static\\courses\\";

    public AssignmentAnswer getAssignmentAnswerById(Long id) {
        return assignmentAnswerRepository.findById(id).orElse(null);
    }

    public void addAssignmentAnswer(AssignmentAnswer assignmentAnswer) throws IOException {
        String studentId = assignmentAnswer.getAnswerStudent().getId();
        Long assignmentId = assignmentAnswer.getAssignment().getId();
        String courseName = assignmentAnswer.getAssignment().getAssignmentBatch().getBatchCourse().getName().trim();
        Long batchId = assignmentAnswer.getAssignment().getAssignmentBatch().getId();
        if (assignmentAnswerRepository.existsByAnswerStudent_IdAndAssignment_Id(studentId, assignmentId)) {
            AssignmentAnswer resAnswer = assignmentAnswerRepository.findByAnswerStudent_IdAndAssignment_Id(studentId,
                    assignmentId);
            fileService.deleteFile(path + courseName + "\\" + Long.toString(batchId)
                    + "\\AssignmentAnswer\\" + resAnswer.getAnswerFileName());
            fileUploadUtilService.saveFile(
                    path + courseName + "\\" + Long.toString(batchId)
                            + "\\AssignmentAnswer\\",
                    studentId + assignmentAnswer.getAnswerFile().getOriginalFilename(),
                    assignmentAnswer.getAnswerFile());
            resAnswer.setAnswerFileName(studentId + assignmentAnswer.getAnswerFileName());
            assignmentAnswerRepository.save(resAnswer);
        } else {
            fileUploadUtilService.saveFile(
                    path + courseName + "\\" + Long.toString(batchId)
                            + "\\AssignmentAnswer\\",
                    studentId + assignmentAnswer.getAnswerFile().getOriginalFilename(),
                    assignmentAnswer.getAnswerFile());
            assignmentAnswer.setAnswerFileName(studentId + assignmentAnswer.getAnswerFile().getOriginalFilename());
            assignmentAnswerRepository.save(assignmentAnswer);
        }
    }

    public void deleteAssignmentAnswerById(Long id) {
        assignmentAnswerRepository.deleteById(id);
    }

    public List<AssignmentAnswer> getAssignmentAnswerListByAssignmentId(Long id) {
        return assignmentAnswerRepository.findByAssignment_Id(id);
    }

}
