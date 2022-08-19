package com.ace.ailpv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.BatchHasResource;
import com.ace.ailpv.repository.BatchHasResourceRepository;

@Service
public class BatchHasResourceService {

    @Autowired
    private BatchHasResourceRepository batchHasResourceRepository;

    public void addBatchHasResource(BatchHasResource batchHasResource) {
        batchHasResourceRepository.save(batchHasResource);
    }

    public void deleteBatchHasResourceById(Long id) {
        batchHasResourceRepository.deleteById(id);
    }

    public BatchHasResource getBatchHasResourceByBatchIdAndResourceId(Long batchId, Long resourceId) {
        return batchHasResourceRepository.findByBatchIdAndResourceId(batchId, resourceId);
    }

}
