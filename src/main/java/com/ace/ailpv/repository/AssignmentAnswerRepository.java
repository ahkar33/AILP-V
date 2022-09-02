package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.AssignmentAnswer;

public interface AssignmentAnswerRepository extends JpaRepository<AssignmentAnswer, Long> {

    Boolean existsByAnswerStudent_IdAndAssignment_Id(String studentId, Long assignmentId);

    AssignmentAnswer findByAnswerStudent_IdAndAssignment_Id(String studentId, Long assignmentId);

    List<AssignmentAnswer> findByAssignment_Id(Long id); 

}
