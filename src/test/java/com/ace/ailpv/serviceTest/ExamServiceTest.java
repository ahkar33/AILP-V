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

import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.repository.ExamRepository;
import com.ace.ailpv.service.ExamService;


@SpringBootTest
public class ExamServiceTest {

    @Mock
    ExamRepository examRepository;
    @InjectMocks
    ExamService examService;

    @Test
    public void addExamTest(){
        Exam exam=getExam();
        examService.addExam(exam);
        verify(examRepository,times(1)).save(exam);
    }

    @Test
    public void getAllExamsTest(){
        List<Exam>examList =getExamList();
        when(examRepository.findAll()).thenReturn(examList);
        List<Exam>list=examService.getAllExams();
        assertEquals(examList.size(), list.size());
        verify(examRepository, times(1)).findAll();
    }

    @Test
    public void getExamByIdTest(){
        Exam exam=getExam();
        when(examRepository.findById(exam.getId())).thenReturn(Optional.of(exam));
        Exam selectExam=examService.getExamById(exam.getId());
        assertEquals(exam.getName(), selectExam.getName());
        assertEquals(exam.getQuestionList(), selectExam.getQuestionList());
        assertEquals(exam.getFullMark(), selectExam.getFullMark());
        assertEquals(exam.getExamCourse(), selectExam.getExamCourse());
        verify(examRepository,times(1)).findById(exam.getId());
    }

    @Test
    public void deleteExamByIdTest(){
        Exam exam=getExam();
        examService.deleteExamById(exam.getId());
        verify(examRepository,times(1)).deleteById(exam.getId());
    }

    @Test
    public void getExamListByBatchIdTest(){
        List<Exam>examList=getExamList();
        when(examRepository.findByExamCourse_Id(1L)).thenReturn(examList);
        List<Exam>list=examService.getExamListByCourseId(1L);
        assertEquals(examList.size(), list.size());
        verify(examRepository,times(1)).findByExamCourse_Id(1L);
    }


    private Exam getExam(){
        Exam exam=new Exam();
        exam.setId(1L);
        exam.setName("first term");
        exam.setFullMark(100L);
        exam.setQuestionList(new ArrayList<>());
        exam.setExamCourse(new Course());
        return exam;
    }

    private List<Exam> getExamList() {
        List<Exam>examList=new ArrayList<>();
        Exam exam=new Exam();
        exam.setId(1L);
        exam.setName("first term");
        exam.setFullMark(100L);
        exam.setQuestionList(new ArrayList<>());
        exam.setExamCourse(new Course());
        Exam exam1=new Exam();
        exam1.setId(1L);
        exam1.setName("first term");
        exam1.setFullMark(100L);
        exam1.setQuestionList(new ArrayList<>());
        exam1.setExamCourse(new Course());
        examList.add(exam);
        examList.add(exam1);
        return examList;
    }
}
