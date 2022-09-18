package com.ace.ailpv.ControllerTest;

import com.ace.ailpv.SecretConfigProperties;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecretConfigProperties secretConfigProperties;
    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    private FileValidationService fileValidationService;
    @MockBean
    private FileUploadUtilService fileUploadUtilService;
    @MockBean
    private FileService fileService;
    @MockBean
    private BatchService batchService;
    @MockBean
    private UserService userService;
    String apiPath = "/user";

    @Test
    public void getTeacherProfileTest() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        String uid = sessionAttr.get("uid").toString();
        User teacher = getTeacher();
        String encodePass = passwordEncoder.encode(teacher.getPassword());
        secretConfigProperties.setDefaultTchPassword(teacher.getPassword().trim());
        teacher.setPassword(encodePass);

        when(userService.getUserById(uid)).thenReturn(teacher);

        this.mockMvc.perform(get(apiPath + "/profile").sessionAttrs(sessionAttr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dpwarn"))
                .andExpect(view().name("/teacher/TCH-PRF-08"));
    }

    @Test
    public void getStudentProfileTest() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        String uid = sessionAttr.get("uid").toString();
        User student = getStudent();
        String encodePass = passwordEncoder.encode(student.getPassword());
        secretConfigProperties.setDefaultStdPassword(student.getPassword().trim());
        student.setPassword(encodePass);

        when(userService.getUserById(uid)).thenReturn(student);

        this.mockMvc.perform(get(apiPath + "/profile").sessionAttrs(sessionAttr))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dpwarn"))
                .andExpect(view().name("/student/STU-PRF-08"));

    }

    @Test
    public void getAdminProfileTest() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        String uid = sessionAttr.get("uid").toString();
        User user = getAdmin();
        when(userService.getUserById(uid)).thenReturn(user);
        this.mockMvc.perform(get(apiPath + "/profile").sessionAttrs(sessionAttr))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/ADM-PRF-16"));

    }

    @Test
    public void changeNameTest() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        this.mockMvc.perform(post(apiPath + "/change-name").sessionAttrs(sessionAttr)
                .param("name", "student"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/profile"));

    }

    @Test
    public void OldPasswordIncorrectTest() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        String uid = sessionAttr.get("uid").toString();
        User user = getTeacher();
        when(userService.getUserById(uid)).thenReturn(user);
        this.mockMvc.perform(post(apiPath + "/change-password").sessionAttrs(sessionAttr)
                .param("oldPassword", "old")
                .param("newPassword", "new")
                .param("confirmPassword", "new")

                .flashAttr("msg", "Old password is incorrect"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void changePasswordTestOK() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        String uid = sessionAttr.get("uid").toString();
        User user = getTeacher();
        String encodePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePass);
        when(userService.getUserById(uid)).thenReturn(user);
        this.mockMvc.perform(post(apiPath + "/change-password").sessionAttrs(sessionAttr)
                .param("oldPassword", "password")
                .param("newPassword", "newPass")
                .param("confirmPassword", "newPass")

                .flashAttr("msg", "isSuccess"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void PasswordDoNotMatchTest() throws Exception {
        HashMap<String, Object> sessionAttr = getSessionAttr();
        String uid = sessionAttr.get("uid").toString();
        User user = getTeacher();
        String encodePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePass);
        when(userService.getUserById(uid)).thenReturn(user);
        this.mockMvc.perform(post(apiPath + "/change-password").sessionAttrs(sessionAttr)
                .param("oldPassword", "password")
                .param("newPassword", "Pass01")
                .param("confirmPassword", "Pass02")
                .flashAttr("msg", "New password and confirm password do not match"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void uploadProfilePicFailTest() throws Exception {
        // File file = new File("E://Shared Data/Images/xyz.jpg");
        // DiskFileItem fileItem = new DiskFileItem("file", "image/png", false, file.getName(),
        //                             (int) file.length(), file.getParentFile());
        // FileInputStream input = new FileInputStream(fileItem);
        // MultipartFile multipartFile = new MockMultipartFile("fileItem",
        //     fileItem.getName(), "image/png", IOUtils.toByteArray(input));


        // MultipartFile file = new MultipartFile()[] ;
        // this.mockMvc.perform(post(apiPath + "/upload")
        // .param("profile_pic",)

    }

    private User getStudent() {
        User student = new User();
        student.setId("stu01");
        student.setName("Joey");
        student.setPassword("password");
        student.setRole("ROLE_STUDENT");
        student.setBatchList(new ArrayList<Batch>());

        return student;
    }

    private User getTeacher() {
        User teacher = new User();
        teacher.setId("tcr01");
        teacher.setName("Joey");
        teacher.setPassword("password");
        teacher.setRole("ROLE_TEACHER");
        teacher.setBatchList(new ArrayList<Batch>());
        return teacher;
    }

    private User getAdmin() {
        User admin = new User();
        admin.setId("adm01");
        admin.setName("Joey");
        admin.setPassword("password");
        admin.setRole("ROLE_ADMIN");
        admin.setBatchList(new ArrayList<Batch>());
        return admin;
    }

    private HashMap<String, Object> getSessionAttr() {
        HashMap<String, Object> sessionAttr = new HashMap<>();
        sessionAttr.put("uid", "tcr01");
        sessionAttr.put("batchId", "1");
        return sessionAttr;
    }

}

