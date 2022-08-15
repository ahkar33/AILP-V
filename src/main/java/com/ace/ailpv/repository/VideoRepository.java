package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.ailpv.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
 @Query(value = "SELECT name FROM ailpv.video WHERE course_id=?1", nativeQuery=true)  
 public List<Video> getVideoByCourseId(String courseId); 

 List<Video> findByCourseId_id(Long id);

}
