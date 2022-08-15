package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ailpv.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Boolean existsByName(String name);

    List<Video> findByVideoCourse_id(Long id);

}
