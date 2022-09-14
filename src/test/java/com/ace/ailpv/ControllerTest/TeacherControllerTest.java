package com.ace.ailpv.ControllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    HttpSession httpSession;

    @MockBean
    UserService userService;

    String apiPath = "/teacher";

    @Test
    public void setupTeacherDashboardTest() throws Exception{
        String teacherId = "tch001";
        HashMap<String, Object> sessionattr = new HashMap<String, Object>(); 
        sessionattr.put("teacherId", teacherId);
        List<Batch> batchList = getBatchList();
        when(userService.getTeacherBatchListById(teacherId)).thenReturn(batchList);
        
        this.mockMvc.perform(get("/teacher/dashboard").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("batchList"))
                .andExpect(view().name("/teacher/TCH-DSB-01"));                                             
    }

    @Test
    public void setupTeacherPublicChat() throws Exception{
        String teacherId = "tch001";
        HashMap<String, Object> sessionattr = new HashMap<String, Object>(); 
        sessionattr.put("teacherId", teacherId);
        User teacherInfo = getTeacher();
        List<Batch> batchList = getBatchList();
        when(userService.getUserById(teacherId)).thenReturn(teacherInfo);
        when(userService.getTeacherBatchListById(teacherInfo.getId())).thenReturn(batchList);

        this.mockMvc.perform(get("/teacher/teacher-public-chat").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userId", teacherInfo.getId()))
                .andExpect(model().attribute("username", teacherInfo.getName()))
                // .andExpect(model().attributeExists("batchId"))
                // .andExpect(model().attributeExists("batchName"))
                .andExpect(model().attribute("batchList", batchList))
                .andExpect(view().name("/teacher/TCH-PBC-05"));

    }



    private Course getCourse() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("java");
        course1.setFee(20000.00);
        course1.setDescription("create course");

        return course1;
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

    private User getTeacher(){
        User user = new User();
        user.setId("tch001");
        user.setName("teacher");
        user.setPassword("password");
        user.setRole("ROLE_TEACHER");
        user.setProfile_pic("profile.png");
        return user;
    }

    
}
