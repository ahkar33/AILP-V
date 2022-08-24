package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.UserSchedule;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {
   
    UserSchedule findByUserIdAndScheduleId(String userId, Long scheduleId);

}
