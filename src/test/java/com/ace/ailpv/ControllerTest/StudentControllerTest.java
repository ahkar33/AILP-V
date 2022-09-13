package com.ace.ailpv.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ace.ailpv.entity.Assignment;
import com.ace.ailpv.entity.AssignmentResult;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.BatchHasExam;
import com.ace.ailpv.entity.BatchHasResource;
import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.entity.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.ace.ailpv.repository.BatchHasResourceRepository;
import com.ace.ailpv.repository.BatchHasVideoRepository;
import com.ace.ailpv.repository.BatchRepository;
import com.ace.ailpv.repository.UserRepository;
import com.ace.ailpv.security.CustomUserDetailsService;
import com.ace.ailpv.service.AssignmentResultService;
import com.ace.ailpv.service.AssignmentService;
import com.ace.ailpv.service.BatchHasExamService;
import com.ace.ailpv.service.BatchHasResourceService;
import com.ace.ailpv.service.BatchHasVideoService;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.UserService;

import java.time.LocalDateTime;
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

}
