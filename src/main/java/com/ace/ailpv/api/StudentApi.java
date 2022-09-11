package com.ace.ailpv.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.UserSchedule;
import com.ace.ailpv.SecretConfigProperties;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Schedule;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.ScheduleService;
import com.ace.ailpv.service.UserScheduleService;
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

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SecretConfigProperties secretConfigProperties;

    @Autowired
    private UserScheduleService userScheduleService;

    @PostMapping("/addStudents")
    public void addStudents(@RequestBody User[] studentList) {
        Long batchId = Long.parseLong(studentList[0].getBatchId());
        Batch batch = batchService.getBatchById(batchId);
        for (User student : studentList) {
            student.getBatchList().add(batch);
            student.setPassword(passwordEncoder.encode(secretConfigProperties.getDefaultStdPassword()));
            student.setRole("ROLE_STUDENT");
            student.setEnabled(true);
            student.setProfile_pic("profile.png");
            userService.addUser(student);
        }

    }

    @GetMapping("/getStudentListByBatchId/{batchId}")
    public List<User> getStudentListByBatchId(@PathVariable("batchId") Long batchId) {
        return userService.getStudentListByBatchId(batchId);
    }

    @PostMapping("/modifyAttendance")
    public void modifyAttendance(@RequestBody UserSchedule[] userScheduleList, HttpSession session) {
        for (UserSchedule userSchedule : userScheduleList) {
            if (!scheduleService.checkSchedule(userSchedule.getDate())) {

                Schedule schedule = new Schedule();
                schedule.setDate(userSchedule.getDate());
                scheduleService.addSchedule(schedule);
            }

            User student = userService.getUserById(userSchedule.getStudentId());
            userSchedule.setUser(student);

            Batch studentBatch = student.getBatchList().get(0);
            userSchedule.setUserScheduleBatch(studentBatch);

            Schedule resSchedule = scheduleService.getScheduleByDate(userSchedule.getDate());
            userSchedule.setSchedule(resSchedule);

            userSchedule.setStatus(userSchedule.getStatus());

            UserSchedule resUserSchedule = userScheduleService.getUserScheduleByUserIdAndScheduleId(student.getId(),
                    resSchedule.getId());

            if (resUserSchedule != null) {
                userScheduleService.deleteUserScheduleById(resUserSchedule.getId());
            }

            userScheduleService.addUserSchedule(userSchedule);

        }
    }

}
