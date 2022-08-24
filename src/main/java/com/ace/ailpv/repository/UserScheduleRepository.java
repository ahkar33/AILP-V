package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ace.ailpv.entity.UserSchedule;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {

    UserSchedule findByUserIdAndScheduleId(String userId, Long scheduleId);

    @Query
    (
        value = "select * from user_schedule  join batch_has_user on batch_has_user.user_id = user_schedule.user_id where batch_has_user.batch_id = ?1 and schedule_id = ?2",
        nativeQuery = true
     )
    List<UserSchedule> findUserScheduleByBatchIdAndScheduleId(Long batchId, Long scheduleId);

    @Query
    (
        value = "select * from user_schedule  join batch_has_user on batch_has_user.user_id = user_schedule.user_id where batch_has_user.batch_id = ?1",
        nativeQuery = true
     )
    List<UserSchedule> findUserScheduleByBatchIdOrScheduleId(Long batchId);

}