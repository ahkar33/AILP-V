package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String>{

    boolean existsById(String id);

}
