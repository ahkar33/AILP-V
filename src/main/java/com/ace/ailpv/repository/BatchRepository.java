package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Batch;

public interface BatchRepository extends JpaRepository<Batch, Long>{
    
}
