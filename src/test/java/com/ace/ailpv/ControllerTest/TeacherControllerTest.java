package com.ace.ailpv.ControllerTest;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    UserScheduleService userScheduleService;
    @MockBean
    ScheduleService scheduleService;
    @MockBean
    AssignmentService assignmentService;
    @MockBean
    AssignmentAnswerService assignmentAnswerService;
    @MockBean
    AssignmentResultService assignmentResultService;
    @MockBean
    ExamService examService;
    @MockBean
    BatchHasExamService batchHasExamService;

    String apiPath = "/teacher";
    @Test
    public void setupTeacherDashboardTest()throws Exception{
        HashMap<String,Object>sessionAttr = getSessionAttr();
        List<Batch>batchList = getBatchList();
        when(userService.getTeacherBatchListById("tcr01")).thenReturn(batchList);
        this.mockMvc.perform(get(apiPath+"/dashboard").sessionAttrs(sessionAttr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("batchList"))
                .andExpect(model().attributeExists("teacherId"))
                .andExpect(view().name("/teacher/TCH-DSB-01"));
    }

    @Test
    public void setupTeacherPublicChatTest()throws Exception{
        HashMap<String,Object>sessionAttr = getSessionAttr();
        User user = getUser();
        List<Batch>batchList = getBatchList();
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(userService.getTeacherBatchListById(user.getId())).thenReturn(batchList);
        this.mockMvc.perform(get(apiPath+"/teacher-public-chat").sessionAttrs(sessionAttr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userId"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("batchId"))
                .andExpect(model().attributeExists("batchName"))
                .andExpect(model().attributeExists("batchList"))
                .andExpect(view().name("/teacher/TCH-PBC-05"));
    }

    @Test
    public void setupChatWithBatchTest()throws Exception{
        HashMap<String,Object>sessionAttr = getSessionAttr();
        User user = getUser();
        List<Batch>batchList = getBatchList();
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(userService.getTeacherBatchListById(user.getId())).thenReturn(batchList);
        this.mockMvc.perform(get(apiPath+"/chatWithBatch").sessionAttrs(sessionAttr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userId"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("batchId"))
                .andExpect(model().attributeExists("batchName"))
                .andExpect(model().attributeExists("batchList"))
                .andExpect(view().name("/teacher/TCH-PBC-05"));
    }

    @Test
    public void setupModifyAttendanceTest()throws Exception{
        HashMap<String,Object>sessionAttr = getSessionAttr();
        List<Batch>batchList = getBatchList();
        when(userService.getTeacherBatchListById("tcr01")).thenReturn(batchList);
        this.mockMvc.perform(get(apiPath+"/modifyAttendance").sessionAttrs(sessionAttr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userScheduleList"))
                .andExpect(model().attributeExists("batchList"))
                .andExpect(model().attributeExists("data"))
                .andExpect(view().name("/teacher/TCH-PBC-08"));


    }



       private HashMap<String,Object> getSessionAttr(){
        HashMap<String,Object>sessionAttr = new HashMap<>();
        sessionAttr.put("uid", "stu001");
        sessionAttr.put("batchId", "1");
        return sessionAttr;
    }

        private List<Batch> getBatchList(){
        List<Batch>batchList = new ArrayList<>();
        Batch batch = new Batch();
            batch.setId(1L);
            batch.setName("java");
            batchList.add(batch);
            return batchList;
        }

        private User getUser(){
        User user = new User();
        user.setId("tcr01");
        user.setName("teacher");
        return  user;
        }
}
