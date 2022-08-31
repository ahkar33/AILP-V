package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ace.ailpv.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Boolean existsByNameAndVideoCourse_id(String name, Long id);

    List<Video> findByVideoCourse_id(Long id);

    @Query(value = "SELECT * FROM video ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Video findFirstVideo();

}
