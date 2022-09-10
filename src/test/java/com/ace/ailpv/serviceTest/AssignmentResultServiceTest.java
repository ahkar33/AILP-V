package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.AssignmentAnswer;
import com.ace.ailpv.entity.AssignmentResult;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.AssignmentResultRepository;
import com.ace.ailpv.service.AssignmentResultService;

@SpringBootTest
public class AssignmentResultServiceTest {

    @Mock
    AssignmentResultRepository assignmentResultRepository;

    @InjectMocks
    AssignmentResultService assignmentResultService;

    @Test
    public  void addAssignmentResultTest(){
        AssignmentResult result = getAssignmentResult();
        assignmentResultService.addAssignmentResult(result);
        verify(assignmentResultRepository,times(1)).save(result);
    }

    @Test
    public void getAssignmentResultListByStudentIdTest(){
        List<AssignmentResult>resultList = getAssignmentResultList();
        when(assignmentResultRepository.findByAssignmentResultAnswer_AnswerStudent_Id("1L")).thenReturn(resultList);
        List<AssignmentResult>resultListActual= assignmentResultService.getAssignmentResultListByStudentId("1L");
        assertEquals(resultList.size(), resultListActual.size());
        verify(assignmentResultRepository,times(1)).findByAssignmentResultAnswer_AnswerStudent_Id("1L");
    }

    @Test
    public void getAssignmentResultByAnswerIdTest(){
        AssignmentResult result = getAssignmentResult();
        when(assignmentResultRepository.findByAssignmentResultAnswer_Id(result.getId())).thenReturn(result);
        AssignmentResult resultActual = assignmentResultService.getAssignmentResultByAnswerId(result.getId());
        assertEquals(result.getComment(), resultActual.getComment());
        assertEquals(result.getMark(), resultActual.getMark());
        assertEquals(result.getAssignmentResultAnswer(), resultActual.getAssignmentResultAnswer());
        verify(assignmentResultRepository,times(1)).findByAssignmentResultAnswer_Id(result.getId());
    }


    private AssignmentResult getAssignmentResult(){
        AssignmentResult result = new AssignmentResult();
        result.setId(1L);
        result.setComment("Good");
        result.setMark("100");
        result.setAssignmentResultAnswer(new AssignmentAnswer());
        result.setAssignmentResultTeacher(new User());
        return result;
    }

    List<AssignmentResult>getAssignmentResultList(){
        List<AssignmentResult>resultList = new ArrayList<>();
        AssignmentResult result = new AssignmentResult();
        result.setId(1L);
        result.setComment("Good");
        result.setMark("100");
        result.setAssignmentResultAnswer(new AssignmentAnswer());
        result.setAssignmentResultTeacher(new User());
        resultList.add(result);
        return resultList;
    }

    
}
