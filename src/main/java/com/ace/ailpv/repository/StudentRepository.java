package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
    
}
