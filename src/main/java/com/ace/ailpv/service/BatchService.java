package com.ace.ailpv.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.BatchRepository;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private UserService userService;

    public void addBatch(Batch batch) {
        try {
            batchRepository.save(batch);
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void deleteBatchById(Long id) {
        List<User> userList = userService.findUserByBatchId(id);
        for(User user : userList) {
            Set<Batch> filteredBatchList = user.getBatchList().stream()
                .filter(batch -> batch.getId() != id)
                .collect(Collectors.toSet());
            user.getBatchList().clear();
            user.getBatchList().addAll(filteredBatchList);
            if(user.getBatchList().size() == 0) {
                userService.deleteUserById(user.getId());
            }else {
                userService.addUser(user);
            }
        }
        batchRepository.deleteById(id);
    }

    public Batch getBatchById(Long id) {
        return batchRepository.findById(id).get();
    }

    public Batch getBatchByUserId(String id) {
        return null; 
    }

    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    public List<Batch> getBatchesByCourseId(Long id) {
        return batchRepository.findByBatchCourse_Id(id);
    }

    public int getBatchCount(){
        return batchRepository.batchCount();
    }


}
