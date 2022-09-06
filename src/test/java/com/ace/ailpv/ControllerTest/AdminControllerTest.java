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
import org.springframework.web.multipart.MultipartFile;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.CourseService;
import com.ace.ailpv.service.ExamService;
import com.ace.ailpv.service.UserService;
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;
    @MockBean
    private BatchService batchService;
    @MockBean
    private ExamService examService;
    @MockBean
    private UserService userService;

    String apiPath = "/admin";

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
      this.mockMvc.perform(post(apiPath + "/addCourse").flashAttr("course", new Course()))
        .andExpect(status().is(302))
          //      .andExpect(flash().attribute("msg", "course name already exists"))
                .andExpect(redirectedUrl("/admin/course-table"));
    }

    @Test
    public void deleteCourseTest() throws Exception {
        this.mockMvc.perform(get(apiPath + "/deleteCourse")
                .param("id", "1")
                .param("courseName", "java"))
            
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


}
