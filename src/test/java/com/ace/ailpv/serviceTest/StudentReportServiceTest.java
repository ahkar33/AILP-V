package com.ace.ailpv.serviceTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.StudentReport;
import com.ace.ailpv.repository.StudentReportRepository;
import com.ace.ailpv.service.StudentReportService;


@SpringBootTest
public class StudentReportServiceTest {

    @Mock
    StudentReportRepository studentReportRepository;

    @InjectMocks
    StudentReportService studentReportService;
    
    @Test
    public void deleteStudentByIdTest (){
        studentReportService.deleteStudentById(1L);
        verify(studentReportRepository,times(1)).deleteById(1L);
    }

    @Test
    public void getAllDataTest(){
        studentReportService.getAllData();
        verify(studentReportRepository,times(1)).findAll();
    }

    @Test
    public void addStudentReport(){
        StudentReport stuReport = new StudentReport();
        stuReport.setId(1L);
        stuReport.setName("Student Report");
        stuReport.setBatch("batch");
        stuReport.setAttendance_percentage("100%");
        stuReport.setStudent_id("stu01");
        studentReportService.addStudentReport(stuReport);
        verify(studentReportRepository,times(1)).save(stuReport);
    }

    
}
