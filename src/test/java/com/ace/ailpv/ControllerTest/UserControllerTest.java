package com.ace.ailpv.ControllerTest;

import com.ace.ailpv.SecretConfigProperties;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.CourseRepository;
import com.ace.ailpv.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private  FileValidationService fileValidationService;
    @MockBean
    private FileUploadUtilService fileUploadUtilService;
    @MockBean
    private FileService fileService;
    @MockBean
    private BatchService batchService;
    @MockBean
    private UserService userService;
    @MockBean
    private SecretConfigProperties secretConfigProperties;

    String apiPath = "/user";
    @Test
    public void changeNameTest()throws Exception{
        HashMap<String, Object> sessionAttr = getSessionAttr();
//        User user = getUser();
//        when(userService.updateByUserName(user.getName(),user.getId()));
        this.mockMvc.perform(post(apiPath + "/change-name").sessionAttrs(sessionAttr))
                //.param("name","name")
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/profile"));

    }

    @Test
    public void cOldPasswordIncorrectTest() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        this.mockMvc.perform(post(apiPath + "/change-password").sessionAttrs(sessionAttr))
               // .param("name","name")
              //  .flashAttr("msg", "Old password is incorrect")
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void changePasswordTestOK() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        this.mockMvc.perform(post(apiPath + "/change-password").sessionAttrs(sessionAttr))
                // .param("name","name")
                //  .flashAttr("msg", "isSuccess")
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void PasswordDoNotMatchTest() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        this.mockMvc.perform(post(apiPath + "/change-password").sessionAttrs(sessionAttr))
                // .param("name","name")
                //  .flashAttr("msg", "New password and confirm password do not match")
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void uploadProfilePicTest()throws Exception{

    }



    private User getUser(){
        User user = new User();
        user.setId("stu001");
        user.setName("Joey");
        return  user;
    }

    private Batch getBatch(){
        Batch batch = new Batch();
        batch.setId(1L);
        batch.setName("java");
        return batch;
    }

    private HashMap<String,Object> getSessionAttr(){
        HashMap<String,Object>sessionAttr = new HashMap<>();
        sessionAttr.put("uid", "stu001");
        sessionAttr.put("batchId", "1");
        return sessionAttr;
    }





}
