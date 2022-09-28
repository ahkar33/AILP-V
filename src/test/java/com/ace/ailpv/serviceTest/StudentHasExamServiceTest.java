package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.entity.StudentHasExam;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.StudentHasExamRepository;
import com.ace.ailpv.service.StudentHasExamService;

@SpringBootTest
public class StudentHasExamServiceTest {
   @Autowired
   StudentHasExamRepository studentHasExamRepository;
   
   @InjectMocks
   StudentHasExamService studentHasExamService;

   @Test
   public void addStudentHasExamTest(){
        StudentHasExam stuExam = getStuExam();
        studentHasExamService.addStudentHasExam(stuExam);
        verify(studentHasExamRepository,times(1)).save(stuExam);
   }

   @Test
   public void getStudentHasExamByStudentIdAndExamIdTest(){
    StudentHasExam stuExam = getStuExam();
    when(studentHasExamService.getStudentHasExamByStudentIdAndExamId("stu01", 1L)).thenReturn(stuExam);
    StudentHasExam stuExamActual = studentHasExamService.getStudentHasExamByStudentIdAndExamId("stu01", 1L);
    assertEquals(stuExam.getId(), stuExamActual.getId());
    assertEquals(stuExam.getScore(), stuExamActual.getScore());
    assertEquals(stuExam.getSheExam(), stuExamActual.getSheExam());
    verify(studentHasExamRepository,times(1)).findBySheStudent_IdAndSheExam_Id("stu01", 1L);
   }

   @Test
   public void getStudentHasExamListByStudentIdTest(){
    List<StudentHasExam>sheList = new ArrayList<>();
    sheList.add(getStuExam());
    when(studentHasExamService.getStudentHasExamListByStudentId("stu01")).thenReturn(sheList);
    List<StudentHasExam>sheListActual = studentHasExamService.getStudentHasExamListByStudentId("stu01");
    assertEquals(sheList.size(),sheListActual.size());
    verify(studentHasExamRepository,times(1)).findBySheStudent_Id("stu01");
   }
   
   
   private StudentHasExam getStuExam(){
    StudentHasExam stuExam = new StudentHasExam();
    stuExam.setId(1L);
    stuExam.setScore(1L);
    stuExam.setSheExam(new Exam());
    stuExam.setSheStudent(new User());
    return stuExam;
   }
}
