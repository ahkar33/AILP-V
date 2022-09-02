package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.AssignmentResult;

public interface AssignmentResultRepository extends JpaRepository<AssignmentResult, Long>{
   
    List<AssignmentResult> findByAssignmentResultAnswer_AnswerStudent_Id(String id);

}
