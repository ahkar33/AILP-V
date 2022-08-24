package com.ace.ailpv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.UserSchedule;
import com.ace.ailpv.repository.UserScheduleRepository;

@Service
public class UserScheduleService {

    @Autowired
    private UserScheduleRepository userScheduleRepository;

    public void addUserSchedule(UserSchedule userSchedule) {
        userScheduleRepository.save(userSchedule);
    }

    public void deleteUserScheduleById(Long id) {
        userScheduleRepository.deleteById(id);
    }

    public UserSchedule getUserScheduleByUserIdAndScheduleId(String userId, Long scheduleId) {
        return userScheduleRepository.findByUserIdAndScheduleId(userId, scheduleId);
    }

}
