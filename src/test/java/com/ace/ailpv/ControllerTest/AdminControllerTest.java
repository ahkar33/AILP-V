package com.ace.ailpv.ControllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Resource;
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
        List<Course> courseList = new ArrayList<>();
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
        this.mockMvc.perform(post(apiPath + "/addCourse").flashAttr("course", course))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/course-table"));
    }

    @Test
    public void addCourseTestFail() throws Exception {
        ResultActions rs = this.mockMvc.perform(post(apiPath + "/addCourse").flashAttr("course", new Course()));
        rs.andExpect(status().is(302))
          //      .andExpect(flash().attribute("msg", "course name already exists"))
                .andExpect(redirectedUrl("/admin/course-table"));
    }


    @Test
    public void editCourseTest() throws Exception{

        // long id = 1L;

        // Course oldCourse = getCourse();
        // Course editCourse = new Course();
        // editCourse.setId(id);
        // editCourse.setName("name");
        // editCourse.setFee(2000.0);
        // editCourse.setDescription("description");
        // when(courseRepository.findById(id)).thenReturn(Optional.of(oldCourse));
        // when(courseRepository.save(any(Course.class))).thenReturn(editCourse);
        // this.mockMvc.perform(post(apiPath+"/editCourse")
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(mapper.writeValueAsString(editCourse)))
        // .andExpect(status().isOk())
        // .andExpect(jsonPath("$.id").value(editCourse.getId()))
        // .andExpect(jsonPath("$.name").value(editCourse.getName()))
        // .andExpect(jsonPath("$.fee").value(editCourse.getFee()));
                
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
                // .andExpect(model().attributeExists("videoList"))
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
                //.andExpect(model().attributeExists("resourceList"))
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
                //.andExpect(model().attributeExists("videoList"))
                //.andExpect(model().attributeExists("course"))
                //.andExpect(model().attributeExists("msg"))
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
                //.andExpect(model().attributeExists("videoList"))
                //.andExpect(model().attributeExists("course"))
                //.andExpect(model().attributeExists("msg"))
                .andExpect(redirectedUrl("/admin/editResource/"+course.getId()));
    }

    @Test
    public void uploadCourseVideoTest() throws Exception{
        Long courseId = 1L;
        Course course = getCourse();
        when(courseService.getCourseById(courseId)).thenReturn(course);
        this.mockMvc.perform(post(apiPath+"/uploadCourseVideo")
                .param("cid", courseId)
                .param()
                .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/editVideo/"+courseId));
    }

    @Test
    public void uploadCourseResourceTest(){

    }

    @Test
    public void deleteCourseTest() throws Exception {
        // this.mockMvc.perform(get(apiPath + "/deleteCourse")
        //         .param("id", "1")
        //         .param("courseName", "java"))
        //         .andExpect(status().is(302))
        //         .andExpect(redirectedUrl("/admin/course-table"));
    }

    @Test
    public void setupExamTableTest() throws Exception {
        this.mockMvc.perform(get(apiPath + "/exam-table"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/ADM-ETB-05"));
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

    private List<Video> getVideoList() {
        return null;
    }

    private List<Resource> getResourceList() {
        return null;
    }

    private Video getVideo(){
        Video video = new Video();
        video.setId(1L);
        video.setName("video name");
        video.setLength("18");
        video.setVideoCourse(getCourse());

        return video;
    }

    private Resource getResource(){
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setName("resource name");
        resource.setResourceCourse(getCourse());
        return resource;
    }


}