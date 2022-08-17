package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.VideoRepository;
import com.ace.ailpv.service.VideoService;

@SpringBootTest
public class VideoServiceTest {
    @Mock
    VideoRepository videoRepository;

    @InjectMocks
    VideoService videoService;

    @Test
    public void isExistByVideoNameTest(){
        String videoName = "something";
        videoService.isExistByVideoName(videoName);
        verify(videoRepository,times(1)).existsByName(videoName);
    }

    @Test
    public void getAllVideosTest(){
        List<Video> videoList = displayAllVideo();
        videoList = videoService.getAllVideos();
        System.out.println(videoList); // I just added that line to avoid warning
        verify(videoRepository,times(1)).findAll();
    }

    @Test
    public void getVideoByIdTest(){
        Video video = getOneVideo();
        Mockito.when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        //verify(videoRepository, times(1)).findById(1L);
        Video actualResponse = videoService.getVideoById(1L);
        assertEquals(actualResponse.getName(), video.getName());
    }

    @Test
    public void deleteVideoByIdTest(){
        videoService.deleteVideoById(1L);
        verify(videoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getVideoByCourseIdTest(){
        List<Video> video = displayAllVideo();
        Mockito.when(videoService.getVideoByCourseId(3L)).thenReturn(video);
        List<Video> result = videoRepository.findByVideoCourse_id(3L);
        assertEquals(result.size(), video.size());
        
    }

    @Test
    public void addVideoTest(){
        Video video  = getOneVideo();
        videoService.addVideo(video);
        verify(videoRepository, times(1)).save(video);
    }

    private Video getOneVideo(){
        Course videoCourse = new Course();
        videoCourse.setId(3L);

        Video video = new Video();
        video.setId(1L);
        video.setName("blah blah");
        video.setVideoCourse(videoCourse);
         
        return video;
    }

    private List<Video> displayAllVideo(){
        
        List<Video> videoList = new ArrayList<Video>();

        Course videoCourse = new Course();
        videoCourse.setId(3L);

        Video video1 = new Video();
        video1.setId(1L);
        video1.setName("blah blah");
        video1.setVideoCourse(videoCourse);

        Video video2 = new Video();
        video2.setId(2L);
        video2.setName("blah blah");
        video2.setVideoCourse(videoCourse);

        videoList.add(video1);
        videoList.add(video2);
        return videoList;
    }
}