package com.ace.ailpv.serviceTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.repository.ExamRepository;
import com.ace.ailpv.service.ExamService;

@SpringBootTest
public class ExamServiceTest {

    @Mock
    ExamRepository examRepository;
    @InjectMocks
    ExamService examService;

    public void addExamTest(){

    }

    // private Exam getExam(){
    //     Exam exam=new Exam();
    //     exam.setId(1L);
    //     exam.setName("first term");

    //     return exam;
    // }
}
