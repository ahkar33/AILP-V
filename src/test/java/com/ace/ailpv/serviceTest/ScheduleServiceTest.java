package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.Schedule;
import com.ace.ailpv.repository.ScheduleRepository;
import com.ace.ailpv.service.ScheduleService;

@SpringBootTest
public class ScheduleServiceTest {
    @Mock
    ScheduleRepository scheduleRepository;

    @InjectMocks
    ScheduleService scheduleService;

    @Test
    public void checkScheduleTest(){
        Schedule schedule=getSchedule();
        scheduleService.checkSchedule(schedule.getDate());
        verify(scheduleRepository,times(1)).existsByDate(schedule.getDate());
    }

    @Test
    public void addScheduleTest(){
        Schedule schedule=getSchedule();
        scheduleService.addSchedule(schedule);
        verify(scheduleRepository,times(1)).save(schedule);
    }

    @Test
    public void getScheduleByDateTest(){
        Schedule schedule=getSchedule();
        when(scheduleRepository.findByDate(schedule.getDate())).thenReturn(schedule);
        Schedule scheduleActual=scheduleService.getScheduleByDate(schedule.getDate());
        assertEquals(schedule.getDate(), scheduleActual.getDate());
        assertEquals(schedule.getUserScheduleList(), scheduleActual.getUserScheduleList());
        verify(scheduleRepository,times(1)).findByDate(schedule.getDate());

    }

    private Schedule getSchedule(){
        Schedule schedule=new Schedule();
        schedule.setId(1L);
        schedule.setDate(LocalDate.of(2022, 10, 10));
        schedule.setUserScheduleList(new ArrayList<>());
        return schedule;
    }
}
