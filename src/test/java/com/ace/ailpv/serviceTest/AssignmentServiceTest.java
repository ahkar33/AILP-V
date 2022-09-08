package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.Assignment;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.repository.AssignmentRepository;
import com.ace.ailpv.service.AssignmentService;
import com.ace.ailpv.service.FileUploadUtilService;

@SpringBootTest
public class AssignmentServiceTest {

    @Mock
    AssignmentRepository assignmentRepository;

    @InjectMocks
    AssignmentService assignmentService;
    @InjectMocks
    FileUploadUtilService fUploadUtilService;

    @Test
    public void getAllAssignmentsTest(){
        List<Assignment>assignmentList=getAssignmentList();
        when(assignmentRepository.findAll()).thenReturn(assignmentList);
        List<Assignment>assignmentListActual=assignmentService.getAllAssignments();
        assertEquals(assignmentList.size(), assignmentListActual.size());
        verify(assignmentRepository,times(1)).findAll();
    }

    @Test
    public void addAssignmentTest(){

    }

    @Test
    public void getAssignmentByIdTest(){
        Assignment assignment =getAssignment();
        when(assignmentRepository.findById(assignment.getId())).thenReturn(Optional.of(assignment));
        Assignment assignmentActual=assignmentService.getAssignmentById(assignment.getId());
        assertEquals(assignment.getName(), assignmentActual.getName());
        assertEquals(assignment.getQuestionFileName(), assignmentActual.getQuestionFileName());
        assertEquals(assignment.getStartTime(), assignmentActual.getStartTime());
        verify(assignmentRepository,times(1)).findById(assignment.getId());

    }

    @Test
    public void getAllAssignmentByBatchIdTest(){
        List<Assignment>assignmentList=getAssignmentList();
        when(assignmentRepository.findByAssignmentBatch_Id(1L)).thenReturn(assignmentList);
        List<Assignment>assignmentactual=assignmentService.getAllAssignmentByBatchId(1L);
        assertEquals(assignmentList.size(), assignmentactual.size());
        verify(assignmentRepository,times(1)).findByAssignmentBatch_Id(1L);

    }


    private Assignment getAssignment(){
        Assignment assignment= new Assignment();
        assignment.setId(1L);
        assignment.setName("Test1");
        assignment.setStartTime(LocalDateTime.now());
        assignment.setQuestionFileName("Test 1 ");
        assignment.setAssignmentBatch(new Batch());
        return assignment;
    }

    private List<Assignment>getAssignmentList(){
        List<Assignment>assignmentList=new ArrayList<>();
        Assignment assignment= new Assignment();
        assignment.setId(1L);
        assignment.setName("Test1");
        assignment.setStartTime(LocalDateTime.now());
        assignment.setQuestionFileName("Test 1 ");
        assignment.setAssignmentBatch(new Batch());
        assignmentList.add(assignment);
        return assignmentList;
    }

    
}
