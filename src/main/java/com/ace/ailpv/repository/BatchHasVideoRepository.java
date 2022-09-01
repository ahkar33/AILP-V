package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.BatchHasVideo;

public interface BatchHasVideoRepository extends JpaRepository<BatchHasVideo, Long> {

    List<BatchHasVideo> findByBhvBatch_Id(Long id);

    BatchHasVideo findByBhvBatch_IdAndVideo_Id(Long batchId, Long resourceId);


}
