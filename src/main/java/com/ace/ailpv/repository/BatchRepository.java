package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ace.ailpv.entity.Batch;

public interface BatchRepository extends JpaRepository<Batch, Long>{ 
    List<Batch> findByBatchCourse_Id(Long id);

    @Query(value = "select count(id) from batch",nativeQuery = true)
    int batchCount();

}
