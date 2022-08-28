package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ace.ailpv.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource,Long> {

    Boolean existsByNameAndResourceCourse_id(String name, Long id);

    List<Resource> findByResourceCourse_id(Long id);
    
}
