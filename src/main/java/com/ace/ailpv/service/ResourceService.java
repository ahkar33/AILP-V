package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.repository.ResourceRepository;

@Service
public class ResourceService {
    @Autowired
    ResourceRepository resourceRepository;

    public List<Resource>getAllResources(){
        return resourceRepository.findAll();
    }
    
    public List<Resource>getResourceByCourseId(Long courseId){
        return resourceRepository.findByCourseId_id(courseId);
    }

    public Resource getResourceById(Long resourceId){
        return resourceRepository.findById(resourceId).get();
    }
}
