package com.ace.ailpv.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.CourseRepository;


@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FileService fileService;

    public void addCourse(Course course) throws IllegalStateException, IOException {

        fileService.createFolderForCourse(course.getName());

        Set<Video> videoList = new HashSet<>();
        Set<Resource> resourceList = new HashSet<>();

        for (MultipartFile video : course.getVideos()) {
            fileService.createFile(video, course.getName() + "\\video");
            videoList.add(new Video(video.getOriginalFilename()));
        }

        for (MultipartFile resource : course.getResources()) {
            fileService.createFile(resource, course.getName() + "\\resource");
            resourceList.add(new Resource(resource.getOriginalFilename()));
        }

        course.getVideoList().addAll(videoList);
        course.getResourceList().addAll(resourceList);

        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void deleteCourseById(Long courseId, String courseName) throws IOException {
        fileService.deleteFolder(courseName);
        courseRepository.deleteById(courseId);
    }

    public Boolean checkCourseName(String name) {
        return courseRepository.existsByName(name);
    }

}