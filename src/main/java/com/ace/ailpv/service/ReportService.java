package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.StudentReport;
import com.ace.ailpv.entity.User;

@Service
public class ReportService {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentReportService studentReportService;

    @Autowired
    private UserScheduleService userScheduleService;

    public void exportReport() {
        createReportTable(3L);
    }

    public void createReportTable(Long batchId) {
        List<User> studentList = userService.getStudentListByBatchId(batchId);
        List<StudentReport> studentReportList = studentReportService.getAllData();

        if (studentReportService.getAllData() != null) {
            for (StudentReport student : studentReportList) {
                studentReportService.deleteStudentById(student.getId());
            }
        }

        for (User student : studentList) {
            StudentReport reqStudent = new StudentReport();
            reqStudent.setStudentId(student.getId());
            reqStudent.setName(student.getName());
            reqStudent.setBatch(student.getBatchList().get(0).getName());
            Float attendancePercentage = userScheduleService.avgAttendaceOfStudent(student.getId()).floatValue();
            reqStudent.setAttendancePercentage(Math.round(attendancePercentage) + "%");
            studentReportService.addStudentReport(reqStudent);
        }

    }

}
