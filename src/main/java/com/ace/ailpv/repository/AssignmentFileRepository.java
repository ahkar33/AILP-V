package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.AssignmentFile;

public interface AssignmentFileRepository extends JpaRepository<AssignmentFile, Long> {
    
}
