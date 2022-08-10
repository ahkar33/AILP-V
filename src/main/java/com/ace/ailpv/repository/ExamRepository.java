package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {

}