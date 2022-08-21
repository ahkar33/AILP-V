package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.BatchHasResource;

public interface BatchHasResourceRepository extends JpaRepository<BatchHasResource, Long> {
  
    List<BatchHasResource> findByBatch_Id(Long id);

    // Boolean existsByBatchIdAndResourceIdAndSchedule(Long batchId, Long resourceId, LocalDateTime schedule);    

    BatchHasResource findByBatchIdAndResourceId(Long batchId, Long resourceId);    

}
