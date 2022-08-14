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
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.UserService;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin(origins = "*")
public class TeacherApi {

    @Autowired
    private BatchService batchService;

    @Autowired
    private UserService userService;

    @PostMapping("/addTeachers")
    public void addTeachers(@RequestBody User[] teacherList) {
        Long batchId = Long.parseLong(teacherList[0].getBatchId());
        Batch batch = batchService.getBatchById(batchId);
        Set<Batch> batchList = new HashSet<>();
        batchList.add(batch);
        for (User teacher : teacherList) {
            if (userService.checkUserId(teacher.getId())) {
                Set<Batch> teacherBatchList = userService.getUserById(teacher.getId()).getBatchList();
                teacherBatchList.addAll(batchList);
                teacher.getBatchList().addAll(teacherBatchList);
                teacher.setPassword("ailp123");
                teacher.setRole("teacher");
                userService.addUser(teacher);
            } else {
                teacher.getBatchList().add(batch);
                teacher.setPassword("ailp123");
                teacher.setRole("teacher");
                userService.addUser(teacher);
            }
        }
    }

}
