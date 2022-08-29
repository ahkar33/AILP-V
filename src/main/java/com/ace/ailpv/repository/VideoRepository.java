package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Boolean existsByNameAndVideoCourse_id(String name, Long id);

    List<Video> findByVideoCourse_id(Long id);

}
