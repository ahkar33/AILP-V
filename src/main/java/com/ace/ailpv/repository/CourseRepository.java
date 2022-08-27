package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ace.ailpv.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Boolean existsByName(String name);

    Course findByName(String name);

    @Query(value = "select count(id) from course",nativeQuery = true)
    int courseCount();

}
