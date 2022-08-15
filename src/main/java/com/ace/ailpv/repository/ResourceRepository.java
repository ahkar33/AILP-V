package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ace.ailpv.entity.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Long> {

    List<Resource> findByCourseId_id(Long id);
    
}
