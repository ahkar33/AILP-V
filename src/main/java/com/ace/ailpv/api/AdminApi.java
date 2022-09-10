package com.ace.ailpv.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.CourseService;
import com.ace.ailpv.service.UserScheduleService;
import com.ace.ailpv.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class AdminApi {

    @Autowired
    UserScheduleService userScheduleService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/getBatchAttendance")
    public Map<String, Float> getBatchAttendance() {
        Map<String, Float> data = new LinkedHashMap<>();
        for (Batch batch : batchService.getAllBatches()) {
            data.put(batch.getName(), userScheduleService.avgAttendaceOfBatch(batch.getId()).floatValue());
        }
        return data;
    }

    @GetMapping(value = "/getStudentAttendance")
    public Map<String, Float> getStudentAttendance() {
        Map<String, Float> data = new LinkedHashMap<>();
        for (User user : userService.getAllStudents()) {
            data.put(user.getName(), userScheduleService.avgAttendaceOfStudent(user.getId()).floatValue());
        }
        return data;
    }

    @GetMapping(value = "/getStudentAttendanceByBatch/{batchId}")
    public Map<String, Float> getStudentAttendanceByBatch(@PathVariable("batchId") String batchId) {
        Map<String, Float> data = new LinkedHashMap<>();
        for (User user : userService.getStudentListByBatchId(Long.parseLong(batchId))) {
            data.put(user.getName(), userScheduleService.avgAttendaceOfStudent(user.getId()).floatValue());
        }
        return data;
    }

    // @GetMapping("/countUserByCourseIdAndRole/{courseId}/{role}")
    // public int countUserByCourseId(@PathVariable("courseId") Long courseId, @PathVariable("role") String role) {
    //     return userRepository.countByBatchList_batchCourse_IdAndRole(courseId, role);
    // }

    @GetMapping("/getStudentCountByCourse")
    public Map<String, Integer> getStudentCountByCourse() {
        List<Course> courseList = courseService.getAllCourses();
        Map<String, Integer> data = new LinkedHashMap<>();
        for(Course course : courseList) {
            data.put(course.getName(), userService.getUserCountByCourseIdAndRole(course.getId(), "ROLE_STUDENT"));
        }
        return data;
    }

}
