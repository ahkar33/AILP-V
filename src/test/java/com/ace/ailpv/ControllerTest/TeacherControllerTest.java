package com.ace.ailpv.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    HttpSession httpSession;

    String apiPath = "/teacher";

    @Test
    public void setupTeacherDashboardTest() throws Exception{
        String teacherId =  "tch001";
        httpSession.setAttribute("teacherId", teacherId);
        Mockito.when(httpSession. getAttribute("teacherId"))
        .thenReturn("tch001");

        this.mockMvc.perform(get("/teacher/student-table"))
                .andExpect(status().isOk())
                .andExpect(view().name("/teacher/TCH-STB-11"));                            
                        
    }

    @Test
    public void setupStudentTableTest() throws Exception{
        
    }

    
}
