package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.UserSchedule;
import com.ace.ailpv.repository.ScheduleRepository;
import com.ace.ailpv.repository.UserScheduleRepository;

@Service
public class UserScheduleService {

    @Autowired
    private UserScheduleRepository userScheduleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleRepository scheduleRepository;


    public void addUserSchedule(UserSchedule userSchedule) {
        userScheduleRepository.save(userSchedule);
    }

    public void deleteUserScheduleById(Long id) {
        userScheduleRepository.deleteById(id);
    }

    public UserSchedule getUserScheduleByUserIdAndScheduleId(String userId, Long scheduleId) {
        return userScheduleRepository.findByUserIdAndScheduleId(userId, scheduleId);
    }

    public List<UserSchedule> getAllUserSchedules() {
        return userScheduleRepository.findAll();
    }

    public List<UserSchedule> getUserScheduleListByBatchIdAndScheduleId(Long batchId, Long scheduleId) {
        return userScheduleRepository.findUserScheduleByBatchIdAndScheduleId(batchId, scheduleId);
    }

    public List<UserSchedule> getUserScheduleListByBatchIdOrScheduleId(Long batchId) {
        return userScheduleRepository.findUserScheduleByBatchIdOrScheduleId(batchId);
    }

    public Long getPresentByBatchId(Long batchId) {
        return userScheduleRepository.countPresentByBatchId(batchId);
    }

    public Long getPresentByStudentId(String studentId) {
        return userScheduleRepository.countPresentByStudentId(studentId);
    }

    public Float avgAttendaceOfBatch(Long batchId) {
        Float totalPresentOfBatch = getPresentByBatchId(batchId).floatValue();
        int  totalStudent = userService.getUserCountByUserRole("ROLE_STUDENT");
        Long totalDate = scheduleRepository.countDate();

        return (totalPresentOfBatch / (totalDate * totalStudent)) * 100;
    }

    public Float avgAttendaceOfStudent(String studentId) {
        float totalPresentOfStudent = getPresentByStudentId(studentId).floatValue();
        
        Long totalDate = scheduleRepository.countDate();
        

        return (totalPresentOfStudent / totalDate)  * 100;
    }

}
