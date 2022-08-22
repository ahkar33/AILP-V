package com.ace.ailpv.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<BatchHasResource> getAllBatchHasResourcesByBatchId(Long id) {
        List<BatchHasResource> unfilteredList = batchHasResourceRepository.findByBatch_Id(id);
        Long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        List<BatchHasResource> filteredList = unfilteredList.stream()
                .filter(batchHasResource -> currentTime >= batchHasResource.getSchedule().toEpochSecond(ZoneOffset.UTC))
                .collect(Collectors.toList());
        return filteredList;
    }

}
