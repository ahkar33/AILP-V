package com.ace.ailpv.api;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Teacher;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.TeacherService;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin(origins = "*")
public class TeacherApi {

    @Autowired
    private BatchService batchService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/addTeachers")
    public void addTeachers(@RequestBody Teacher[] teacherList) {
        Long batchId = Long.parseLong(teacherList[0].getBatchId());
        Batch batch = batchService.getBatchById(batchId);
        Set<Batch> batchList = new HashSet<>();
        batchList.add(batch);
        for (Teacher teacher : teacherList) {
            if (teacherService.checkTeacherId(teacher.getId())) {
                Set<Batch> teacherBatchList = teacherService.getTeacherById(teacher.getId()).getBatchList();
                teacherBatchList.addAll(batchList);
                teacher.getBatchList().addAll(teacherBatchList);
                teacher.setPassword("ailp123");
                teacherService.addTeacher(teacher);
            } else {
                teacher.getBatchList().addAll(batchList);
                teacher.setPassword("ailp123");
                teacherService.addTeacher(teacher);
            }
        }
    }

}
