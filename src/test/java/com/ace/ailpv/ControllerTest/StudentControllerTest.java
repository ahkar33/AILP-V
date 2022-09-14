package com.ace.ailpv.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

import com.ace.ailpv.entity.*;
import com.ace.ailpv.service.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ailpv.repository.BatchHasResourceRepository;
import com.ace.ailpv.repository.BatchHasVideoRepository;
import com.ace.ailpv.repository.BatchRepository;
import com.ace.ailpv.repository.UserRepository;
import com.ace.ailpv.security.CustomUserDetailsService;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

// import static org.mockito.BDDMockito.*;

import java.util.HashMap;
//import org.springframework.security.test.context.support.WithUserDetails;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
@ContextConfiguration
// @WithUserDetails("stu001")
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AssignmentService assignmentService;

    @MockBean
    AssignmentResultService assignmentResultService;

    @MockBean
    BatchHasExamService batchHasExamService;

    @MockBean
    BatchHasResourceService batchHasResourceService;

    @MockBean
    BatchHasResourceRepository batchHasResourceRepository;

    @MockBean
    BatchHasVideoService batchHasVideoService;

    @MockBean
    BatchHasVideoRepository batchHasVideoRepository;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    BatchService batchService;

    @MockBean
    VideoService videoService;

    @MockBean
    BatchRepository batchRepository;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    String apiPath = "/student";

    // Testing student home page
    @Test
    public void showStudentHomePageTest() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("uid", "stu001");
        sessionattr.put("batchId", "1");
        this.mockMvc.perform(get("/student/student-home").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userId"))
                .andExpect(model().attributeExists("batchId"))
                .andExpect(view().name("/student/STU-HOM-01"));
    }

    @Test
    public void setupStudentPublicChatTest() throws Exception {
        User user = new User();
        user.setId("stu001");
        user.setName("Joey");
        Batch batch = new Batch();
        batch.setId(1L);
        batch.setName("java");
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("uid", "stu001");
        sessionattr.put("batchId", "1");
        when(userService.getUserById("stu001")).thenReturn(user);
        when(batchService.getBatchById(1L)).thenReturn(batch);
        this.mockMvc.perform(get(apiPath + "/student-public-chat").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userId"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("batchId"))
                .andExpect(model().attributeExists("batchName"))
                .andExpect(view().name("/student/STU-PBC-07"));
    }

    @Test
    public void setupGetResourceTest() throws Exception {
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setName("hello");
        BatchHasResource batchHasResource = new BatchHasResource();
        batchHasResource.setResource(resource);
        batchHasResource.setSchedule(LocalDateTime.now());
        List<BatchHasResource> bhrList = new ArrayList<>();
        bhrList.add(batchHasResource);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("batchId", "1");
        when(batchHasResourceService.getAllBatchHasResourcesByBatchId(1L)).thenReturn(bhrList);
        this.mockMvc.perform(get("/student/getResources").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("batchHasResourceList"))
                .andExpect(view().name("/student/STU-REC-09"));
    }

    @Test
    public void getStudentAssignment() throws Exception {
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setName("hello");
        assignment.setDueDate(LocalDateTime.now().toString());
        assignment.setQuestionFileName("hello.zip");
        List<Assignment> assignmentList = new ArrayList<>();
        assignmentList.add(assignment);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("batchId", "1");
        when(assignmentService.getAllAssignmentByBatchId(1L)).thenReturn(assignmentList);
        this.mockMvc.perform(get("/student/studentAssignment").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("assignmentList"))
                .andExpect(model().attributeExists("answer"))
                .andExpect(view().name("/student/STU-ASG-00"));
    }

    @Test
    public void getStudentGradeBook() throws Exception {
        AssignmentResult result = new AssignmentResult();
        List<AssignmentResult> resultList = new ArrayList<>();
        resultList.add(result);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("uid", "stu001");
        when(assignmentResultService.getAssignmentResultListByStudentId("stu001")).thenReturn(resultList);
        this.mockMvc.perform(get("/student/studentGradeBook").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("resultList"))
                .andExpect(view().name("/student/STU-GRB-00"));
    }

    @Test
    public void getExamListTest() throws Exception {
        Exam exam = new Exam();
        exam.setId(1L);
        BatchHasExam bhe = new BatchHasExam();
        bhe.setBheExam(exam);
        List<BatchHasExam> bheList = new ArrayList<>();
        bheList.add(bhe);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("batchId", "1");
        when(batchHasExamService.getBatchHasExamListByBatchId(1L)).thenReturn(bheList);
        this.mockMvc.perform(get("/student/getExamList").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bheList"))
                .andExpect(view().name("/student/STU-EXL-00"));
    }

    @Test
    public void getExam() throws Exception {
        Exam exam = new Exam();
        exam.setId(1L);
        BatchHasExam bhe = new BatchHasExam();
        bhe.setBheExam(exam);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("uid", "stu001");
        when(batchHasExamService.getBatchHasExamById(1L)).thenReturn(bhe);
        this.mockMvc.perform(get("/student/getExam/{bheId}", "1").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bheId"))
                .andExpect(model().attributeExists("studentId"))
                .andExpect(model().attributeExists("examId"))
                .andExpect(view().name("/student/STU-EXM-00"));
    }

    @Test
    public void submitAssignmentTest() throws Exception {

        User student = new User();
        student.setId("stu001");

        Course course = new Course();
        course.setId(1L);
        course.setName("java");

        Batch batch = new Batch();
        batch.setId(1L);
        batch.setBatchCourse(course);

        Assignment assignment = new Assignment();
        assignment.setAssignmentBatch(batch);
        assignment.setId(1L);
        assignment.setEndTime(LocalDateTime.of(2015,
                Month.JULY, 29, 19, 30, 40));

        File file = new File("src\\test\\resources\\input.txt");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        AssignmentAnswer answer = new AssignmentAnswer();
        answer.setAnswerStudent(student);
        answer.setAssignment(assignment);
        answer.setAnswerFile(multipartFile);

        when(assignmentService.getAssignmentById(1L)).thenReturn(assignment);
        this.mockMvc.perform(post("/student/submitAssignment")
                .flashAttr("answer", answer))
                .andExpect(status().is(302))
                .andExpect(flash().attributeExists("successMsg"))
                .andExpect(redirectedUrl("/student/studentAssignment"));
    }

    @Test
    public void getVideosTestIfBatchIdExists() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("uid", "stu001");
        sessionattr.put("batchId", "1");

        User user = new User();
        user.setId("stu001");
        user.setName("Joey");
        user.setLastWatchVideoId(1L);

        List<User> teacherList = new ArrayList<>();
        teacherList.add(user);

        Course course = new Course();
        course.setId(1L);
        course.setName("Java");

        Batch batch = new Batch();
        batch.setId(1L);
        batch.setName("java");
        batch.setBatchCourse(course);

        Video video = new Video();
        video.setId(1L);
        video.setName("hello");

        BatchHasVideo bhv = new BatchHasVideo();
        bhv.setId(1L);
        bhv.setVideo(video);

        List<BatchHasVideo> bhvList = new ArrayList<>();
        bhvList.add(bhv);

        when(userService.getUserById("stu001")).thenReturn(user);
        when(batchHasVideoService.getAllBatchHasVideoByBatchId(1L)).thenReturn(bhvList);
        when(batchService.getBatchById(1L)).thenReturn(batch);
        when(userService.getTeacherListByBatchId(1L)).thenReturn(teacherList);
        when(videoService.getVideoById(1L)).thenReturn(video);
        this.mockMvc.perform(get("/student/getVideos/{batchId}", "1").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("video"))
                .andExpect(model().attributeExists("teacherList"))
                .andExpect(model().attributeExists("courseName"))
                .andExpect(model().attributeExists("batchHasVideoList"))
                .andExpect(model().attributeExists("batchId"))
                .andExpect(model().attributeExists("username"))
                .andExpect(view().name("/student/STU-VID-06"));
    }

    @Test
    public void getVideosTestIfBatchIdIfAILP() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("uid", "stu001");
        sessionattr.put("batchId", "1");

        User user = new User();
        user.setId("stu001");
        user.setName("Joey");
        user.setLastWatchVideoId(null);

        List<User> teacherList = new ArrayList<>();
        teacherList.add(user);

        Course course = new Course();
        course.setId(1L);
        course.setName("Java");

        Batch batch = new Batch();
        batch.setId(1L);
        batch.setName("java");
        batch.setBatchCourse(course);

        Video video = new Video();
        video.setId(1L);
        video.setName("hello");

        BatchHasVideo bhv = new BatchHasVideo();
        bhv.setId(1L);
        bhv.setVideo(video);

        List<BatchHasVideo> bhvList = new ArrayList<>();
        bhvList.add(bhv);

        when(userService.getUserById("stu001")).thenReturn(user);
        when(batchHasVideoService.getAllBatchHasVideoByBatchId(1L)).thenReturn(bhvList);
        when(batchService.getBatchById(1L)).thenReturn(batch);
        when(userService.getTeacherListByBatchId(1L)).thenReturn(teacherList);
        when(videoService.getVideoById(1L)).thenReturn(video);
        this.mockMvc.perform(get("/student/getVideos/{batchId}", "AILP").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("video"))
                .andExpect(model().attributeExists("teacherList"))
                .andExpect(model().attributeExists("courseName"))
                .andExpect(model().attributeExists("batchHasVideoList"))
                .andExpect(model().attributeExists("batchId"))
                .andExpect(model().attributeExists("username"))
                .andExpect(view().name("/student/STU-VID-06"));
    }

    @Test
    public void getVideosTestIfBatchIdIfVideoListSizeIsZero() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("uid", "stu001");
        sessionattr.put("batchId", "1");

        User user = new User();
        user.setId("stu001");
        user.setName("Joey");
        user.setLastWatchVideoId(null);

        List<User> teacherList = new ArrayList<>();
        teacherList.add(user);

        Course course = new Course();
        course.setId(1L);
        course.setName("Java");

        Batch batch = new Batch();
        batch.setId(1L);
        batch.setName("java");
        batch.setBatchCourse(course);

        BatchHasVideo bhv = new BatchHasVideo();
        bhv.setId(1L);

        List<BatchHasVideo> bhvList = new ArrayList<>();

        when(userService.getUserById("stu001")).thenReturn(user);
        when(batchHasVideoService.getAllBatchHasVideoByBatchId(1L)).thenReturn(bhvList);
        when(batchService.getBatchById(1L)).thenReturn(batch);
        when(userService.getTeacherListByBatchId(1L)).thenReturn(teacherList);
        this.mockMvc.perform(get("/student/getVideos/{batchId}", "AILP").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("video"))
                .andExpect(model().attributeExists("teacherList"))
                .andExpect(model().attributeExists("courseName"))
                .andExpect(model().attributeExists("batchHasVideoList"))
                .andExpect(model().attributeExists("batchId"))
                .andExpect(model().attributeExists("username"))
                .andExpect(view().name("/student/STU-VID-06"));
    }

    @Test
    public void showClickedVideoIfRoleIsTeacherTest() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("uid", "stu001");
        sessionattr.put("batchId", "1");

        User user = new User();
        user.setId("stu001");
        user.setName("Joey");
        user.setRole("ROLE_TEACHER");

        List<User> teacherList = new ArrayList<>();
        teacherList.add(user);

        Course course = new Course();
        course.setId(1L);
        course.setName("Java");

        Batch batch = new Batch();
        batch.setId(1L);
        batch.setName("java");
        batch.setBatchCourse(course);

        Video video = new Video();
        video.setId(1L);
        video.setName("hello");

        BatchHasVideo bhv = new BatchHasVideo();
        bhv.setId(1L);
        bhv.setVideo(video);

        List<BatchHasVideo> bhvList = new ArrayList<>();
        bhvList.add(bhv);

        when(userService.getUserById("stu001")).thenReturn(user);
        when(batchHasVideoService.getAllBatchHasVideoByBatchId(1L)).thenReturn(bhvList);
        when(batchService.getBatchById(1L)).thenReturn(batch);
        when(userService.getTeacherListByBatchId(1L)).thenReturn(teacherList);
        when(videoService.getVideoById(1L)).thenReturn(video);
        this.mockMvc.perform(get("/student/showClickedVideo/{courseName}/{videoId}/{batchId}", "Java", "1", "1").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("teacherList"))
                .andExpect(model().attributeExists("video"))
                .andExpect(model().attributeExists("courseName"))
                .andExpect(model().attributeExists("batchHasVideoList"))
                .andExpect(model().attributeExists("batchId"))
                .andExpect(model().attributeExists("username"))
                .andExpect(view().name("/student/STU-VID-06"));
    }

    @Test
    public void showClickedVideoIfRoleIsStudentTest() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("uid", "stu001");
        sessionattr.put("batchId", "1");

        User user = new User();
        user.setId("stu001");
        user.setName("Joey");
        user.setRole("ROLE_STUDENT");

        List<User> teacherList = new ArrayList<>();
        teacherList.add(user);

        Course course = new Course();
        course.setId(1L);
        course.setName("Java");

        Batch batch = new Batch();
        batch.setId(1L);
        batch.setName("java");
        batch.setBatchCourse(course);

        Video video = new Video();
        video.setId(1L);
        video.setName("hello");

        BatchHasVideo bhv = new BatchHasVideo();
        bhv.setId(1L);
        bhv.setVideo(video);

        List<BatchHasVideo> bhvList = new ArrayList<>();
        bhvList.add(bhv);

        when(userService.getUserById("stu001")).thenReturn(user);
        when(batchHasVideoService.getAllBatchHasVideoByBatchId(1L)).thenReturn(bhvList);
        when(batchService.getBatchById(1L)).thenReturn(batch);
        when(userService.getTeacherListByBatchId(1L)).thenReturn(teacherList);
        when(videoService.getVideoById(1L)).thenReturn(video);
        this.mockMvc.perform(get("/student/showClickedVideo/{courseName}/{videoId}/{batchId}", "Java", "1", "1").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("teacherList"))
                .andExpect(model().attributeExists("video"))
                .andExpect(model().attributeExists("courseName"))
                .andExpect(model().attributeExists("batchHasVideoList"))
                .andExpect(model().attributeExists("batchId"))
                .andExpect(model().attributeExists("username"))
                .andExpect(view().name("/student/STU-VID-06"));
    }

}
