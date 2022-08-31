package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.VideoRepository;

@Service
public class VideoService{
    @Autowired
    VideoRepository videoRepository;

    public Boolean isExistByVideoNameAndCourseId(String name, Long id) {
        return videoRepository.existsByNameAndVideoCourse_id(name, id);
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Video getVideoById(Long id) {
        return videoRepository.findById(id).get();
    }

    public void deleteVideoById(Long id) {
         videoRepository.deleteById(id);
    }

    public List<Video> getVideoByCourseId(Long courseId) {
        return videoRepository.findByVideoCourse_id(courseId);
    }

    public void addVideo(Video video){
        videoRepository.save(video);
    }

    public Video getFirstVideo() {
        return videoRepository.findFirstVideo();
    }

}
