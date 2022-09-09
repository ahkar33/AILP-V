package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.AssignmentAnswer;
import com.ace.ailpv.entity.AssignmentResult;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.AssignmentAnswerRepository;
import com.ace.ailpv.service.AssignmentAnswerService;

@SpringBootTest
public class AssignmentAnswerServiceTest {
    @Mock
    AssignmentAnswerRepository assignmentAnswerRepository;

    @InjectMocks
    AssignmentAnswerService assignmentAnswerService;

    @Test
    public void getAssignmentAnswerByIdtest(){
        AssignmentAnswer answer = getAssignmentAnswer();
        when(assignmentAnswerRepository.findById(answer.getId())).thenReturn(Optional.of(answer));
        AssignmentAnswer answerActual = assignmentAnswerService.getAssignmentAnswerById(answer.getId());
        assertEquals(answer.getAnswerFileName(), answerActual.getAnswerFileName());
        assertEquals(answer.getAnswerStudent(), answerActual.getAnswerStudent());
        assertEquals(answer.getAssignemntAnswerResult(), answerActual.getAssignemntAnswerResult());
        verify(assignmentAnswerRepository,times(1)).findById(answer.getId());
    }

    @Test
    public void saveAssignmentAnswerTest(){
        AssignmentAnswer answer = getAssignmentAnswer();
        assignmentAnswerService.saveAssignmentAnswer(answer);
        verify(assignmentAnswerRepository,times(1)).save(answer);
    }

    @Test
    public void addAssignmentAnswerTest(){

    }

    @Test
    public void deleteAssignmentAnswerById(){
        AssignmentAnswer answer = getAssignmentAnswer();
        assignmentAnswerService.deleteAssignmentAnswerById(answer.getId());
        verify(assignmentAnswerRepository,times(1)).deleteById(answer.getId());
    } 

    @Test
    public void getAssignmentAnswerListByAssignmentIdTest(){
        List<AssignmentAnswer>answerList = getAssignmentAnswerList();
        when(assignmentAnswerRepository.findByAssignment_Id(1L)).thenReturn(answerList);
        List<AssignmentAnswer>answerListactual= assignmentAnswerService.getAssignmentAnswerListByAssignmentId(1L);
        assertEquals(answerList.size(), answerListactual.size());
        verify(assignmentAnswerRepository,times(1)).findByAssignment_Id(1L);
    }

    @Test
    public void getAssignmentAnswerListByStudentIdTest(){
       List<AssignmentAnswer> answerlist = getAssignmentAnswerList();
        when(assignmentAnswerRepository.findByAnswerStudent_IdOrderByAssignment_Id("1L")).thenReturn(answerlist);
        List<AssignmentAnswer>answerlistActual = assignmentAnswerService.getAssignmentAnswerListByStudentId("1L");
        assertEquals(answerlist.size(), answerlistActual.size());
        verify(assignmentAnswerRepository,times(1)).findByAnswerStudent_IdOrderByAssignment_Id("1L");
    }

     
    private AssignmentAnswer getAssignmentAnswer(){
        AssignmentAnswer answer = new AssignmentAnswer();
        answer.setId(1L);
        answer.setAnswerFileName("file");
        answer.setAnswerStudent(new User());
        answer.setAssignemntAnswerResult(new AssignmentResult());
        return answer;
    } 
    private List<AssignmentAnswer> getAssignmentAnswerList(){
        List<AssignmentAnswer>answerlist = new ArrayList<>();
        AssignmentAnswer answer = new AssignmentAnswer();
        answer.setId(1L);
        answer.setAnswerFileName("file");
        answer.setAnswerStudent(new User());
        answer.setAssignemntAnswerResult(new AssignmentResult());
        answerlist.add(answer);
        return answerlist;
    }
}
