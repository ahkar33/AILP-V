package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.StudentReport;
import com.ace.ailpv.repository.StudentReportRepository;

@Service
public class StudentReportService {

    @Autowired
    private StudentReportRepository studentReportRepository;

    public void deleteStudentById(Long id) {
        studentReportRepository.deleteById(id);
    }

    public List<StudentReport> getAllData() {
        return studentReportRepository.findAll();
    }

    public void addStudentReport(StudentReport studentReport) {
        studentReportRepository.save(studentReport);
    }

}
