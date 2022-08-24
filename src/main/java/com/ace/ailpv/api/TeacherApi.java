package com.ace.ailpv.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private PasswordEncoder passwordEncoder;

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
                List<Batch> teacherBatchList = userService.getUserById(teacher.getId()).getBatchList();
                teacherBatchList.addAll(batchList);
                teacher.getBatchList().addAll(teacherBatchList);
                teacher.setPassword(passwordEncoder.encode("ailp123"));
                teacher.setRole("ROLE_TEACHER");
                teacher.setIsMute(false);
                teacher.setProfile_pic("profile.png");
                userService.addUser(teacher);
            } else {
                teacher.getBatchList().add(batch);
                teacher.setPassword(passwordEncoder.encode("ailp123"));
                teacher.setRole("ROLE_TEACHER");
                teacher.setIsMute(false);
                teacher.setProfile_pic("profile.png");
                userService.addUser(teacher);
            }
        }
    }

    @GetMapping("/getStudentListByTeacherId/{id}")
    public List<User> getStudentListByTeacherId(@PathVariable("id") String id) {
        return userService.getStudentListByTeacherId(id);
    }

}
