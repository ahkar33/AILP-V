package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Boolean existsByName(String name);

    Course findByName(String name);

}
