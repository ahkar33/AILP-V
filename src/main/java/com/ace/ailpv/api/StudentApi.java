package com.ace.ailpv.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.Student;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.StudentService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentApi {
    
    @Autowired
    private BatchService batchService;    

    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudents")
    public void addStudents(@RequestBody Student[] studentList) {
        Long batchId = Long.parseLong(studentList[0].getBatchId());
        Batch batch = batchService.getBatchById(batchId);
        for(Student student : studentList) {
            student.setStudentBatch(batch);
            student.setPassword("ailp123");
            studentService.addStudent(student);
        }
    }

}
