package com.ace.ailpv.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
    
    Boolean existsByDate(LocalDate date);

    Schedule findByDate(LocalDate date);

    @Query
    (
        value = "SELECT COUNT(id) FROM schedule",
        nativeQuery = true
    )
    Long countDate();

}
