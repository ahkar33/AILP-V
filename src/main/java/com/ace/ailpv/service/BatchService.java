package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.repository.BatchRepository;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    public void addBatch(Batch batch) {
        batchRepository.save(batch);
    }

    // public void updateBatch(Batch batch) {
    //     batchRepository.sav;
    // }

    public void deleteBatchById(Long id) {
        batchRepository.deleteById(id);
    }

    public Batch getBatchById(Long id) {
        return batchRepository.findById(id).get();
    }

    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

}
