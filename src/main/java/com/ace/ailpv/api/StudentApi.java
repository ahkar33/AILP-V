package com.ace.ailpv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.UserService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentApi {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BatchService batchService;

    @Autowired
    private UserService userService;

    @PostMapping("/addStudents")
    public void addStudents(@RequestBody User[] studentList) {
        Long batchId = Long.parseLong(studentList[0].getBatchId());
        Batch batch = batchService.getBatchById(batchId);
        for (User student : studentList) {
            student.getBatchList().add(batch);
            student.setPassword(passwordEncoder.encode("ailp123"));
            student.setRole("ROLE_STUDENT");
            student.setIsMute(false);
            student.setProfile_pic("profile.png");
            userService.addUser(student);
        }
    }

}
