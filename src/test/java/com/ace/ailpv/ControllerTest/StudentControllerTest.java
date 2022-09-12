package com.ace.ailpv.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ace.ailpv.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    String apiPath = "/student";

    MockitoSession mockito;

    @BeforeEach
    public void setup() {
        //initialize session to start mocking
        mockito = Mockito.mockitoSession()
           .initMocks(this)
           .strictness(Strictness.STRICT_STUBS)
           .startMocking();
    }

    @AfterEach 
    public void tearDown() {
        //It is necessary to finish the session so that Mockito
        // can detect incorrect stubbing and validate Mockito usage
        //'finishMocking()' is intended to be used in your test framework's 'tear down' method.
        mockito.finishMocking();
    }

    @Test
    public void showStudentHomePageTest() throws Exception {
  
        this.mockMvc.perform(get(apiPath + "/student-home"))
                .andExpect(status().isOk())
                .andExpect(view().name("/student/STU-HOM-01"));
    }

    private User getUser() {
        User user = new User();
        user.setId("sut001");
        user.setName("student");
        user.setPassword("password");
        user.setRole("ROLE_STUDENT");
        user.setProfile_pic("profile.png");
        return user;
    }

}
