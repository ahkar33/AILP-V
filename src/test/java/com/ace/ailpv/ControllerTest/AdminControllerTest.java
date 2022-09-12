package com.ace.ailpv.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.CourseRepository;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.CourseService;
import com.ace.ailpv.service.ExamService;
import com.ace.ailpv.service.ResourceService;
import com.ace.ailpv.service.UserService;
import com.ace.ailpv.service.VideoService;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private BatchService batchService;
    @MockBean
    private ExamService examService;
    @MockBean
    private UserService userService;
    @MockBean
    private VideoService videoService;
    @MockBean
    private ResourceService resourceService;

    String apiPath = "/admin";

    @Test
    public void setupDashboradTest() throws Exception{
        this.mockMvc.perform(get(apiPath+"/dashboard"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("studentCount"))
                .andExpect(model().attributeExists("teacherCount"))
                .andExpect(model().attributeExists("batchCount"))
                .andExpect(model().attributeExists("courseCount"))
                .andExpect(view().name("/admin/ADM-DSB-01"));
    }

    @Test
    public void setupCourseTableTest() throws Exception {
        Course course = getCourse();
        List<Course> courseList = getCourseList();
        courseList.add(course);
        when(courseService.getAllCourses()).thenReturn(courseList);

        this.mockMvc.perform(get(apiPath + "/course-table"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("course"))
                .andExpect(view().name("/admin/ADM-CTB-04"));
    }

    @Test
    public void addCourseTestOk() throws Exception {
        Course course = getCourse();
    //   when(courseService.addCourse(course));
        this.mockMvc.perform(post(apiPath + "/addCourse")
                .flashAttr("course", course))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/course-table"));
    }

    @Test
    public void addCourseTestFail() throws Exception {
        ResultActions rs = this.mockMvc.perform(post(apiPath + "/addCourse"));
        rs.andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/course-table"));
    }


    @Test
    public void editCourseTest() throws Exception{

        long id = 1L;

        Course oldCourse = getCourse();
        Course editCourse = new Course();
        editCourse.setId(id);
        editCourse.setName("name");
        editCourse.setFee(2000.0);
        editCourse.setDescription("description");
        when(courseRepository.findById(id)).thenReturn(Optional.of(oldCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(editCourse);
        // this.mockMvc.perform(post(apiPath+"/editCourse?multipart/form-data")
        //         .param("id", "1L")
        //         .param("name", "name")
        //         .param("fee", "2000.0")
        //         .param("desc", "description"))
        //     .andExpect(status().is(302))
        //     .andExpect(redirectedUrl("/admin/course-table"));
                
    }

    @Test
    public void editVideoTest() throws Exception{
        long id = 1L;
        Course course = getCourse();
        List<Video> videoList = getVideoList();
        when(courseService.getCourseById(id)).thenReturn(course);
        when(videoService.getVideoByCourseId(id)).thenReturn(videoList);

        this.mockMvc.perform(get(apiPath+"/editVideo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("videoList"))
                .andExpect(model().attributeExists("course"))
                .andExpect(view().name("/admin/ADM-VTB-14"));
    }

    @Test
    public void editResourceTest() throws Exception{
        long id = 1L;
        Course course = getCourse();
        List<Resource> resourceList = getResourceList();
        when(courseService.getCourseById(id)).thenReturn(course);
        when(resourceService.getResourceByCourseId(id)).thenReturn(resourceList);

        this.mockMvc.perform(get(apiPath+"/editResource/{id}", id))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("resourceList"))
                .andExpect(model().attributeExists("course"))
                .andExpect(view().name("/admin/ADM-RTB-15"));
    }

    @Test
    public void deleteVideoTest() throws Exception{
        long vidId = 1L;
        String courseName = "java";
        Video video = getVideo();
        Course course = getCourse();
        List<Video> videoList = getVideoList();
        when(courseService.getCourseByName(courseName)).thenReturn(course);
        when(videoService.getVideoById(vidId)).thenReturn(video);
        when(videoService.getAllVideos()).thenReturn(videoList);

        this.mockMvc.perform(get(apiPath+"/deleteVideo/{vidId}/{courseName}", 1L, "java"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/editVideo/"+course.getId()));
    }

    @Test
    public void deleteResourceTest() throws Exception{
        Long resourceId = 1L;
        String courseName = "java";
        Resource resource = getResource();
        List<Resource> resourceList = getResourceList();
        Course course = getCourse();
        when(resourceService.getResourceById(resourceId)).thenReturn(resource);
        when(courseService.getCourseByName(courseName)).thenReturn(course);
        when(resourceService.getAllResources()).thenReturn(resourceList);

        this.mockMvc.perform(get(apiPath+"/deleteResource/{resourceId}/{courseName}", resourceId, courseName))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/editResource/"+course.getId()));
    }

    @Test
    public void uploadCourseVideoTest() throws Exception{
        Long cid = 1L;
        Course course = getCourse();
        when(courseService.getCourseById(cid)).thenReturn(course);
        
        // final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("file");
        // final MockMultipartFile video = new MockMultipartFile("file", "filename.mp4", "video/mp4", inputStream);
        // this.mockMvc.perform(multipart(apiPath+"/uploadCourseVideo?multipart/form-data")
        //         .file(video)
        //         .param("courseId", "1L"))
        //         .andExpect(status().is(302))
        //         .andExpect(redirectedUrl("/admin/editVideo/"+course.getId()));
    }

    @Test
    public void uploadCourseResourceTest() throws Exception{
        Long cid = 1L;
        Course course = getCourse();
        when(courseService.getCourseById(cid)).thenReturn(course);
        
        // final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("file");
        // MockMultipartFile pdf = new MockMultipartFile("file", "filename.pdf", "application/pdf", inputStream);
        // this.mockMvc.perform(multipart(apiPath+"/uploadCourseResource?multipart/form-data")
        //         .file(pdf)
        //         .param("courseId", "cid"))
        //         .andExpect(status().is(302))
        //         .andExpect(redirectedUrl("/admin/editResource/"+course.getId()));
    }

    @Test
    public void deleteCourseTest() throws Exception {
        Long id = 1L;
        String courseName = "java";

        this.mockMvc.perform(get(apiPath + "/deleteCourse/{id}/{courseName}", id, courseName))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/course-table"));
    }

    @Test
    public void setupExamTableTest() throws Exception {
        this.mockMvc.perform(get(apiPath + "/exam-table"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/ADM-ETB-05"));
    }

    @Test
    public void setupCreateExamTest() throws Exception{
         List<Course> courseList = getCourseList();
         when(courseService.getAllCourses()).thenReturn(courseList);
         this.mockMvc.perform(get(apiPath+"/create-exam"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/ADM-CRE-06"));
    }

    @Test
    public void setupBatchTableTest() throws Exception{
        this.mockMvc.perform(get(apiPath+"/batch-table"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("batch"))
                .andExpect(model().attributeExists("editBatch"))
                .andExpect(model().attributeExists("batchList"))
                .andExpect(view().name("/admin/ADM-BTB-03"));
    }

    @Test
    public void addBatchTest() throws Exception{
        Batch batch = getBatch();
        batch.setIsActive(true);
        Course course = getCourse();
        when(courseService.getCourseById(batch.getBatchCourse().getId())).thenReturn(course);
        batchService.addBatch(batch, course.getName());

        // this.mockMvc.perform(post(apiPath+"/addBatch")
        //         .param("name",batch.getName())
        //         .param("start date", batch.getStartDate().toString())
        //         .param("end date", batch.getEndDate().toString())
        //         .flashAttr("batch", new Batch()))
        //         .andExpect(status().is(302))
        //         .andExpect(redirectedUrl("/admin/batch-table"));
    }

    @Test
    public void deleteBatchTest() throws Exception{
        Long batchId = 1L;
        Batch batch = getBatch();
        when(batchService.getBatchById(batchId)).thenReturn(batch);
        batchService.deleteBatchById(batchId, batch.getBatchCourse().getName());
        this.mockMvc.perform(get(apiPath+"/deleteBatch/{id}", batchId))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/batch-table"));
    }

    @Test
    public void disableBatchTest() throws Exception{
        Long id = 1L;
        Batch batch = getBatch();
        when(batchService.getBatchById(id)).thenReturn(batch);

        this.mockMvc.perform(get(apiPath+"/toggleDisableBatch/{id}", 1L))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/batch-table"));
    }

    @Test
    public void setupEditBatchTest() throws Exception{
        Long id = 1L;
        List<Course> courseList = getCourseList();
        Batch batch = getBatch();
        when(batchService.getBatchById(id)).thenReturn(batch);
        when(courseService.getAllCourses()).thenReturn(courseList);

        this.mockMvc.perform(get(apiPath+"/editBatch/{id}", id))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("batch"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/ADM-EDB-11"));
    }

    @Test
    public void editBatchTest() throws Exception{
        Long id = 1L;
        Batch batch = getBatch();
        when(batchService.getBatchById(id)).thenReturn(batch);
        batch.setIsActive(true);
        batchService.updateBatch(batch);
        // this.mockMvc.perform(post(apiPath+"/editBatch")
        //         .param("name", batch.getName())
        //         .param("start date", batch.getStartDate().toString())
        //         .param("end date", batch.getEndDate().toString())
        //         .flashAttr("batch", new Batch()))
        //         .andExpect(status().is(302))
        //         .andExpect(redirectedUrl("/admin/batch-table"));
    }

    @Test
    public void setupStudentTableTest() throws Exception{
        List<User> studentList = getUserList();
        when(userService.getAllStudents()).thenReturn(studentList);
        this.mockMvc.perform(get(apiPath+"/student-table"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("studentList"))
                .andExpect(view().name("/admin/ADM-STB-08"));
    }

    @Test
    public void setupStudentRegisterTest() throws Exception{
        List<Batch> batchList = getBatchList();
        when(batchService.getAllBatches()).thenReturn(batchList);
        this.mockMvc.perform(get(apiPath+"/studentRegister"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/ADM-STG-07"));
    }

    @Test
    public void deleteStudentTest() throws Exception{
        String uid = "stu001";
        userService.deleteUserById(uid);
        this.mockMvc.perform(get(apiPath+"/deleteStudent/{id}", uid))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/student-table"));
    }

    @Test
    public void setupEditStudentTest() throws Exception{
        String uid = "stu001";
        User student = getUser();
        when(userService.getUserById(uid)).thenReturn(student);

        this.mockMvc.perform(get(apiPath+"/editStudent/{id}", uid))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attributeExists("batchList"))
                .andExpect(view().name("/admin/ADM-EDS-12"));
    }

    @Test
    public void editStudentTest() throws Exception{
        String uid = "stu001";
        User student = getUser();
        userService.addUser(student);
        when(userService.getUserById(uid)).thenReturn(student);

        // this.mockMvc.perform(post(apiPath+"/editStudent"))
        //         .andExpect(status().is(302))
        //         .andExpect(redirectedUrl("/admin/student-table"));
    }

    @Test
    public void deleteExamTest() throws Exception{
        Long id = 1L;
        this.mockMvc.perform(get(apiPath+"/deleteExam/{id}", id))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/exam-table"));
    }

    @Test
    public void setupTeacherTableTest() throws Exception{
        List<User> teacherList = getUserList();
        when(userService.getAllTeachers()).thenReturn(teacherList);
        this.mockMvc.perform(get(apiPath+"/teacher-table"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/ADM-TTB-10"));
    }

    @Test
    public void setupTeacherRegisterTest() throws Exception{
        List<Batch> batchList = getBatchList();
        when(batchService.getAllBatches()).thenReturn(batchList);
        
        this.mockMvc.perform(get(apiPath+"/teacherRegister"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/ADM-TTG-09"));
    }

    @Test
    public void deleteTeacherTest() throws Exception{
        String uid = "tch001";
        this.mockMvc.perform(get(apiPath+"/deleteTeacher/{id}", uid))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/teacher-table"));
    }

    @Test
    public void setupEditTeacherTest() throws Exception{
        String uid = "tch001";
        User teacher = getUser();
        List<Batch> batchList = getBatchList();
        when(userService.getUserById(uid)).thenReturn(teacher);
        when(batchService.getAllBatches()).thenReturn(batchList);

        this.mockMvc.perform(get(apiPath+"/editTeacher/{id}", uid))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("batchList"))
                .andExpect(model().attributeExists("teacher"))
                .andExpect(view().name("/admin/ADM-EDT-13.html"));
    }

    @Test
    public void editTeacherTest() throws Exception{
        // this.mockMvc.perform(post(apiPath+"/editTeacher"))
        //         .andExpect(status().is(302))
        //         .andExpect(redirectedUrl("/admin/teacher-table"));
    }

    @Test
    public void changePasswordTest() throws Exception{
        String uid = "adm001";
        User user = getAdmin();
        String pwd = "updatedPassword";
        when(userService.getUserById(uid)).thenReturn(user);
        userService.updatePasswordByUserId(pwd, uid);

        this.mockMvc.perform(get(apiPath+"/changePassword/{userId}", uid))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/dashboard"));
    }

    @Test
    public void changePasswordStudentTest() throws Exception{
        String uid = "stu001";
        User user = getUser();
        String pwd = "updatedPassword";
        when(userService.getUserById(uid)).thenReturn(user);
        userService.updatePasswordByUserId(pwd, uid);

        this.mockMvc.perform(get(apiPath+"/changePassword/{userId}", uid))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/student-table"));
    }

    @Test
    public void changePasswordTeacherTest() throws Exception{
        String uid = "tch001";
        User user = getTeacher();
        String pwd = "updatedPassword";
        when(userService.getUserById(uid)).thenReturn(user);
        userService.updatePasswordByUserId(pwd, uid);

        this.mockMvc.perform(get(apiPath+"/changePassword/{userId}", uid))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/teacher-table"));
    }

    @Test
    public void registerTest() throws Exception{
        this.mockMvc.perform(get(apiPath+"/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/ADM-REG-17"));
    }

    @Test
    public void setupRegisterTestOk() throws Exception{
        this.mockMvc.perform(post(apiPath+"/register?multipart/form-data")
                .param("id", "adm001")
                .param("name", "admin")
                .param("password", "admin")
                .param("confirmPassword", "admin")
                .param("pin", "1337"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/auth/login"));
        
    }

    @Test
    public void setupRegisterTestFail() throws Exception {
        ResultActions rs = this.mockMvc.perform(post(apiPath+"/register?multipart/form-data")
                                    .param("id", "adm001")
                                    .param("name", "admin")
                                    .param("password", "admin")
                                    .param("confirmPassword", "admin")
                                    .param("pin", "9999"));
        rs.andExpect(status().is(302))
            .andExpect(flash().attribute("errorMsg", "Invalid Pin!"))
            .andExpect(redirectedUrl("/admin/register"));
    }

    @Test
    public void setupRegisterUnmatchPwdTest() throws Exception {
        ResultActions rs = this.mockMvc.perform(post(apiPath+"/register?multipart/form-data")
                                    .param("id", "adm001")
                                    .param("name", "admin")
                                    .param("password", "admin")
                                    .param("confirmPassword", "ailpitb")
                                    .param("pin", "1337"));
        rs.andExpect(status().is(302))
            .andExpect(flash().attribute("errorMsg", "Unmatch Password!"))
            .andExpect(redirectedUrl("/admin/register"));
    }


    private Course getCourse() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("java");
        course1.setFee(20000.00);
        course1.setDescription("create course");
        course1.setVideos(new MultipartFile[] {});
        course1.setResources(new MultipartFile[] {});
        return course1;
    }

    private List<Course> getCourseList(){
        List<Course> courseList = new ArrayList<>();
        Course c1 = getCourse();
        Course c2 = getCourse();
        courseList.add(c1);
        courseList.add(c2);
        return courseList;
    }

    private Video getVideo(){
        Video video = new Video();
        video.setId(1L);
        video.setName("video name");
        video.setLength("18");
        video.setVideoCourse(getCourse());

        return video;
    }

    private List<Video> getVideoList() {
        List<Video> videoList = new ArrayList<>();
        Video v1 = getVideo();
        Video v2 = getVideo();
        videoList.add(v1);
        videoList.add(v2);
        return videoList;
    }

    private Resource getResource(){
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setName("resource name");
        resource.setResourceCourse(getCourse());
        return resource;
    }

    private List<Resource> getResourceList() {
        List<Resource> resouceList = new ArrayList<>();
        Resource r1 = getResource();
        Resource r2 = getResource();
        resouceList.add(r1);
        resouceList.add(r2);
        return resouceList;
    }

    private Batch getBatch(){
        Batch batch = new Batch();
        batch.setId(1L);
        batch.setName("java batch 01");
        batch.setBatchCourse(getCourse());
        batch.setIsActive(false);
        batch.setStartDate(LocalDate.of(2022, 9, 06));
        batch.setEndDate(LocalDate.of(2022, 9, 22));

        return batch;
    }

    private List<Batch> getBatchList(){
        List<Batch> batchList = new ArrayList<>();
        Batch batch1 = getBatch();
        Batch batch2 = getBatch();
        batchList.add(batch1);
        batchList.add(batch2);
        return batchList;
    }

    private User getAdmin(){
        User user = new User();
        user.setId("adm001");
        user.setName("student");
        user.setPassword("password");
        user.setRole("ROLE_ADMIN");
        user.setProfile_pic("profile.png");
        return user;
    }

    private User getUser(){
        User user = new User();
        user.setId("sut001");
        user.setName("student");
        user.setPassword("password");
        user.setRole("ROLE_STUDENT");
        user.setProfile_pic("profile.png");
        return user;
    }

    private User getTeacher(){
        User user = new User();
        user.setId("tch001");
        user.setName("teacher");
        user.setPassword("password");
        user.setRole("ROLE_TEACHER");
        user.setProfile_pic("profile.png");
        return user;
    }

    private List<User> getUserList(){
        List<User> userList = new ArrayList<>();
        User u1 = getUser();
        User u2 = getUser();
        userList.add(u1);
        userList.add(u2);
        return userList;
    }
}