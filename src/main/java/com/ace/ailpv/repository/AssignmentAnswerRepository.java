package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.ailpv.entity.AssignmentAnswer;

@Repository
public interface AssignmentAnswerRepository extends JpaRepository <AssignmentAnswer,Long>{

    @Query
    (
        value = "SELECT A.name FROM assignment_answer Ans join assignment A ON A.id = Ans.assignment_id WHERE Ans.assignment_id = ?1",
        nativeQuery = true
    )
    String getAssignmentNameByAssignmentId(Long assignmentId);

    List<AssignmentAnswer> getAnswerByAssignmentId(Long assignmentId);
    
}
