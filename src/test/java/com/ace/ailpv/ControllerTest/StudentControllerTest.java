package com.ace.ailpv.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.ace.ailpv.repository.BatchHasResourceRepository;
import com.ace.ailpv.repository.BatchHasVideoRepository;
import com.ace.ailpv.repository.BatchRepository;
import com.ace.ailpv.repository.UserRepository;
import com.ace.ailpv.service.BatchHasResourceService;
import com.ace.ailpv.service.BatchHasVideoService;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.UserService;
// import org.springframework.security.test.context.support.WithUserDetails;

// @SpringBootTest(
//     webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
// )

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
// @WithUserDetails("stu01")
public class StudentControllerTest {
    @Autowired
	private MockMvc mockMvc;

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

    String apiPath = "/student";

    @Test
    public void showStudentHomePageTest() throws Exception{
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("uid", "stu01");   
        this.mockMvc.perform(get(apiPath+ "/student-home"))
           .andExpect(status().isOk())
           .andExpect(model().attributeExists("userId"))
           .andExpect(model().attributeExists("batchId"))
           .andExpect(view().name("/student/STU-HOM-01"));
    }

    @Test
    public void getResourcesTest() throws Exception{
        this.mockMvc.perform(get(apiPath + "/getResources"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("batchHasResourceList"))
                .andExpect(view().name("/student/STU-REC-09"));
    }
    
    


}
