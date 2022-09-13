package com.ace.ailpv.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.VideoRepository;

import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

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
        return videoRepository.findById(id).orElse(null);
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

    public String getVideoLength(MultipartFile myFile) throws InputFormatException, EncoderException, IOException{
      Long hour = 0L;
        Long min=0L;
        // Long ses=0L;
        File file=new File(myFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(myFile.getInputStream(),file);
        MultimediaObject instance =new MultimediaObject(file);
        MultimediaInfo result=instance.getInfo(); 
        hour = (result.getDuration()/1000)/3600;
        min=(result.getDuration()/1000)/60;
        Long hourMin = ((result.getDuration()/1000) % 3600) / 60;
        
        // ses=(result.getDuration()/1000)%60;      
        file.delete();
        if(hour == 0) {
            return min + "min";
        }
        return hour + "hr " + hourMin + "min";
    }
}
