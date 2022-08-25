package com.ace.ailpv.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Schedule;
import com.ace.ailpv.repository.ScheduleRepository;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Boolean checkSchedule(LocalDate date) {
        return scheduleRepository.existsByDate(date);
    }

    public void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public Schedule getScheduleByDate(LocalDate date) {
        return scheduleRepository.findByDate(date);
    }

}
