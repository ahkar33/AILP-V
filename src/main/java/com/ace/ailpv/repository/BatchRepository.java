package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Batch;

public interface BatchRepository extends JpaRepository<Batch, Long>{
   
    List<Batch> findByBatchCourse_Id(Long id);

}
