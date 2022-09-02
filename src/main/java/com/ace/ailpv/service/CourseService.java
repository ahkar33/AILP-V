package com.ace.ailpv.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.CourseRepository;

import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private BatchService batchService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ExamService examService;

    public void addCourse(Course course) throws IllegalStateException, IOException, InputFormatException, EncoderException {

        fileService.createFolderForCourse(course.getName().trim());

        courseRepository.save(course);
        Course resCourse = getCourseById(course.getId());

        for (MultipartFile video : course.getVideos()) {
            fileService.createFile(video, course.getName().trim() + "\\video");
            Video reqVideo = new Video();
            reqVideo.setLength(videoService.getVideoLength(video));
            reqVideo.setName(video.getOriginalFilename());
            reqVideo.setVideoCourse(resCourse);
            videoService.addVideo(reqVideo);
        }

        for (MultipartFile resource : course.getResources()) {
            fileService.createFile(resource, course.getName().trim() + "\\resource");
            Resource reqResource = new Resource();
            reqResource.setName(resource.getOriginalFilename());
            reqResource.setResourceCourse(resCourse);
            resourceService.addResource(reqResource);
        }

    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).get();
    }

    public Course getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    public void deleteCourseById(Long courseId, String courseName) throws IOException {
        fileService.deleteFolder(courseName);
        List<Batch> batchList =  batchService.getBatchesByCourseId(courseId);
        for(Batch batch : batchList) {
            batchService.deleteBatchById(batch.getId(), courseName);
        }
        List<Exam> examList = examService.getExamListByBatchId(courseId);
        for(Exam exam: examList) {
            examService.deleteExamById(exam.getId());
        }
        courseRepository.deleteById(courseId);
    }

    public Boolean checkCourseName(String name) {
        return courseRepository.existsByName(name);
    }

    public void updateCourse(Course course) {
        courseRepository.save(course);
    }

    public int getCourseCount(){
        return courseRepository.courseCount();
    }

}