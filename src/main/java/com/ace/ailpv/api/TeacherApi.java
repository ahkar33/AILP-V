package com.ace.ailpv.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.SecretConfigProperties;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.BatchHasResource;
import com.ace.ailpv.entity.BatchHasVideo;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.service.BatchHasResourceService;
import com.ace.ailpv.service.BatchHasVideoService;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.ResourceService;
import com.ace.ailpv.service.UserScheduleService;
import com.ace.ailpv.service.UserService;
import com.ace.ailpv.service.VideoService;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin(origins = "*")
public class TeacherApi {

    @Autowired
    private VideoService videoService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BatchService batchService;

    @Autowired
    private UserService userService;

    @Autowired

    private SecretConfigProperties secretConfigProperties;

    @Autowired
    private BatchHasVideoService batchHasVideoService;

    @Autowired
    private BatchHasResourceService batchHasResourceService;

    @Autowired
    private UserScheduleService userScheduleService;

    // @Autowired
    // private ExamService examService;

    @PostMapping("/addTeachers")
    public void addTeachers(@RequestBody User[] teacherList) {
        Long batchId = Long.parseLong(teacherList[0].getBatchId());
        Batch batch = batchService.getBatchById(batchId);
        Set<Batch> batchList = new HashSet<>();
        batchList.add(batch);
        for (User teacher : teacherList) {
            if (userService.checkUserId(teacher.getId())) {
                List<Batch> teacherBatchList = userService.getUserById(teacher.getId()).getBatchList();
                teacherBatchList.addAll(batchList);
                teacher.getBatchList().addAll(teacherBatchList);
                teacher.setPassword(passwordEncoder.encode(secretConfigProperties.getDefaultTchPassword()));
                teacher.setRole("ROLE_TEACHER");
                teacher.setEnabled(true);
                teacher.setProfile_pic("profile.png");
                userService.addUser(teacher);
            } else {
                teacher.getBatchList().add(batch);
                teacher.setPassword(passwordEncoder.encode(secretConfigProperties.getDefaultTchPassword()));
                teacher.setRole("ROLE_TEACHER");
                teacher.setEnabled(true);
                teacher.setProfile_pic("profile.png");
                userService.addUser(teacher);
            }
        }
    }

    @GetMapping("/getStudentListByTeacherId/{id}")
    public List<User> getStudentListByTeacherId(@PathVariable("id") String id) {
        return userService.getStudentListByTeacherId(id);
    }

    @GetMapping("/getResourceListByBatchId/{batchId}")
    public List<Resource> getResourceListByBatchId(@PathVariable("batchId") Long batchId) {
        Batch batch = batchService.getBatchById(batchId);
        Long courseId = batch.getBatchCourse().getId();
        List<Resource> resourceList = resourceService.getResourceByCourseId(courseId);
        return resourceList;
    }

    @GetMapping("/getResourceListByTeacherId")
    public List<Resource> getResourceListByTeacher(HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        User teacherInfo = userService.getUserById(teacherId);
        List<Course> teacherCourseList = userService.getTeacherCourseListById(teacherInfo.getId());
        List<Resource> resourceList = new ArrayList<>();
        for (Course course : teacherCourseList) {
            resourceList.addAll(course.getResourceList());
        }
        return resourceList;
    }

    @PostMapping("/postResourceForBatch")
    public void postResourceForBatch(@RequestBody BatchHasResource[] bhrList) {
        for (BatchHasResource bhr : bhrList) {
            BatchHasResource resBatchHasResource = batchHasResourceService.getBatchHasResourceByBatchIdAndResourceId(
                    bhr.getBhrBatchId(), bhr.getBhrResourceId());
            if (resBatchHasResource != null) {
                batchHasResourceService.deleteBatchHasResourceById(resBatchHasResource.getId());
            }
            Batch batch = batchService.getBatchById(bhr.getBhrBatchId());
            Resource resource = resourceService.getResourceById(bhr.getBhrResourceId());
            BatchHasResource batchHasResource = new BatchHasResource();
            batchHasResource.setBatch(batch);
            batchHasResource.setResource(resource);
            batchHasResource.setSchedule(bhr.getSchedule());
            batchHasResourceService.addBatchHasResource(batchHasResource);
        }
    }

    // ---------------------------------------------------------------------------------------------------------

    @GetMapping("/getVideoListByBatchId/{batchId}")
    public List<Video> getVideoListByBatchId(@PathVariable("batchId") Long batchId) {
        Batch batch = batchService.getBatchById(batchId);
        Long courseId = batch.getBatchCourse().getId();
        List<Video> videoList = videoService.getVideoByCourseId(courseId);
        return videoList;
    }

    @GetMapping("/getVideoListByTeacherId")
    public List<Video> getVideoListByTeacher(HttpSession session) {
        String teacherId = (String) session.getAttribute("uid");
        User teacherInfo = userService.getUserById(teacherId);
        List<Course> teacherCourseList = userService.getTeacherCourseListById(teacherInfo.getId());
        List<Video> videoList = new ArrayList<>();
        for (Course course : teacherCourseList) {
            videoList.addAll(course.getVideoList());
        }
        return videoList;
    }

    @PostMapping("/postVideoForBatch")
    public void postVideoForBatch(@RequestBody BatchHasVideo[] bhvList) {
        for (BatchHasVideo bhv : bhvList) {
            BatchHasVideo resBatchHasVideo = batchHasVideoService.getBatchHasVideoyBatchIdAndVideoId(
                    bhv.getBhvBatchId(), bhv.getBhvVideoId());
            if (resBatchHasVideo != null) {
                batchHasVideoService.deleteBatchHasVideoById(resBatchHasVideo.getId());
            }
            Batch batch = batchService.getBatchById(bhv.getBhvBatchId());
            Video video = videoService.getVideoById(bhv.getBhvVideoId());
            BatchHasVideo batchHasVideo = new BatchHasVideo();
            batchHasVideo.setBhvBatch(batch);
            batchHasVideo.setVideo(video);
            batchHasVideo.setSchedule(bhv.getSchedule());
            batchHasVideoService.addBatchHasVideo(batchHasVideo);
        }
    }

    @GetMapping("/getStudentCountByBatchTeacherId/{teacherId}")
    public Map<String, Integer> getStudentCountByBatch(@PathVariable("teacherId") String teacherId) {
        List<Batch> batchList = userService.getTeacherBatchListById(teacherId);
        Map<String, Integer> data = new LinkedHashMap<>();
        for (Batch batch : batchList) {
            data.put(batch.getName(), userService.getUserCountByBatchIdAndRole(batch.getId(), "ROLE_STUDENT"));
        }
        return data;
    }

    @GetMapping("/getStudentAttendanceByTeacherId/{teacherId}")
    public Map<String, Float> getStudentAttendance(@PathVariable("teacherId") String teacherId) {
        List<User> studentList = userService.getStudentListByTeacherId(teacherId);
        Map<String, Float> data = new LinkedHashMap<>();
        for (User user : studentList) {
            data.put(user.getName(), userScheduleService.avgAttendaceOfStudent(user.getId()).floatValue());
        }
        return data;
    }

    // @GetMapping("/getExamListByTeacherId")
    // public List<Exam> getExamListByTeacherId() {
    // List<Exam> examList = examService.getExamListByTeacherId("tch001");
    // return examList;
    // }

    // @GetMapping("/getTeacherBatchListByTeacherIdAndCourseId/{teacherId}/{courseId}")
    // public List<Batch>
    // getTeacherBatchListByTeacherIdAndCourseId(@PathVariable("teacherId") String
    // teacherId, @PathVariable("courseId") Long courseId) {
    // List<Batch> batchList =
    // userService.getTeacherBatchListByTeacherIdAndCourseId(teacherId, courseId);
    // return batchList;
    // }

}
