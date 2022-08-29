package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ailpv.entity.AssignmentAnswer;

@Repository
public interface AssignmentAnswerRepository extends JpaRepository <AssignmentAnswer,Long>{

    //List<AssignmentAnswer> findByAssignmentId(Long assignmentId);
    
}
