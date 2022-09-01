package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Schedule;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.UserSchedule;
import com.ace.ailpv.repository.ScheduleRepository;
import com.ace.ailpv.repository.UserRepository;
import com.ace.ailpv.repository.UserScheduleRepository;
import com.ace.ailpv.service.UserScheduleService;
import com.ace.ailpv.service.UserService;

@SpringBootTest
public class UserScheduleServiceTest {
     @Mock
     UserScheduleRepository userScheduleRepository;
     @Mock
     ScheduleRepository scheduleRepository;
     @Mock
     UserRepository userRepository;
     

     @InjectMocks
     UserScheduleService userScheduleService;
     @InjectMocks
     UserService userService;

     @Test
     public void addUserScheduleTest(){
        UserSchedule userSchedule=getUserSchedule();
        userScheduleService.addUserSchedule(userSchedule);
        verify(userScheduleRepository,times(1)).save(userSchedule);
     }

     @Test
     public void deleteUserScheduleByIdTest(){
        UserSchedule userSchedule=getUserSchedule();
        userScheduleService.deleteUserScheduleById(userSchedule.getId());
        verify(userScheduleRepository,times(1)).deleteById(userSchedule.getId());
     }

     @Test
     public void getUserScheduleByUserIdAndScheduleIdTest(){
        UserSchedule userSchedule=getUserSchedule();
        Schedule schedule=getSchedule();
        User user=getUser();
        when(userScheduleRepository.findByUserIdAndScheduleId(user.getId(), schedule.getId())).thenReturn(userSchedule);
        UserSchedule userScheduleActual=userScheduleService.getUserScheduleByUserIdAndScheduleId(user.getId(), schedule.getId());
        assertEquals(userSchedule.getDate(), userScheduleActual.getDate());
        assertEquals(userSchedule.getStatus(), userScheduleActual.getStatus());
        assertEquals(userSchedule.getSchedule(), userScheduleActual.getSchedule());
        verify(userScheduleRepository,times(1)).findByUserIdAndScheduleId(user.getId(), schedule.getId());
     }

     @Test
     public void getAllUserSchedulesTest(){
        List<UserSchedule>userScheduleList=getUserScheduleList();
        when(userScheduleRepository.findAll()).thenReturn(userScheduleList);
        List<UserSchedule>userScheduleListActual=userScheduleService.getAllUserSchedules();
        assertEquals(userScheduleList.size(), userScheduleListActual.size());
        verify(userScheduleRepository,times(1)).findAll();
     }

     @Test
     public void getUserScheduleListByBatchIdAndScheduleIdTest(){
        List<UserSchedule>userScheduleList=getUserScheduleList();
        Schedule schedule=getSchedule();
        Batch batch=getBatch();
        when(userScheduleRepository.findUserScheduleByBatchIdAndScheduleId(batch.getId(), schedule.getId())).thenReturn(userScheduleList);
        List<UserSchedule>userScheduleListActual=userScheduleService.getUserScheduleListByBatchIdAndScheduleId(batch.getId(), schedule.getId());
        assertEquals(userScheduleList.size(), userScheduleListActual.size());
        verify(userScheduleRepository,times(1)).findUserScheduleByBatchIdAndScheduleId(batch.getId(), schedule.getId());

     }

     @Test
     public void getUserScheduleListByBatchIdOrScheduleIdTest(){
        List<UserSchedule>userScheduleList=getUserScheduleList();
        Batch batch=getBatch();
        when(userScheduleRepository.findUserScheduleByBatchIdOrScheduleId(batch.getId())).thenReturn(userScheduleList);
        List<UserSchedule>userScheduleListActual=userScheduleService.getUserScheduleListByBatchIdOrScheduleId(batch.getId());
        assertEquals(userScheduleList.size(), userScheduleListActual.size());
        verify(userScheduleRepository,times(1)).findUserScheduleByBatchIdOrScheduleId(batch.getId());
     }

     @Test
     public void getPresentByBatchIdTest(){
        Batch batch=getBatch();
        userScheduleService.getPresentByBatchId(batch.getId());
        verify(userScheduleRepository,times(1)).countPresentByBatchId(batch.getId());
     }

     @Test
     public void getPresentByStudentId(){
        User user=getUser();
        userScheduleService.getPresentByStudentId(user.getId());
        verify(userScheduleRepository,times(1)).countPresentByStudentId(user.getId());
     }

    //  @Test
    //  public void avgAttendaceOfBatchTest(){
    //     User user=getUser();
    //     Batch batch=getBatch();
    //     when(userService.getUserCountByUserRole(user.getRole()));
    //     when(scheduleRepository.countDate());
    //     userScheduleService.avgAttendaceOfBatch(batch.getId());

    //  }

    // @Test
    // public void avgAttendaceOfStudent(){
    //     User user=getUser();
    //     when(scheduleRepository.countDate());
    //     userScheduleService.avgAttendaceOfStudent(user.getId());
    // }

     private UserSchedule getUserSchedule(){
        UserSchedule userSchedule=new UserSchedule();
        userSchedule.setId(1L);
        userSchedule.setUser(getUser());
        userSchedule.setSchedule(getSchedule());
        userSchedule.setStatus("Present");
        userSchedule.setUserScheduleBatch(new Batch());
        return userSchedule;
     }

     private List<UserSchedule>getUserScheduleList(){
        List<UserSchedule>userScheduleList=new ArrayList<>();
        UserSchedule userSchedule=new UserSchedule();
        userSchedule.setId(1L);
        userSchedule.setUser(getUser());
        userSchedule.setSchedule(getSchedule());
        userSchedule.setStatus("Present");
        userSchedule.setUserScheduleBatch(new Batch());
        userScheduleList.add(userSchedule);
        return userScheduleList;
     }

     private Batch getBatch(){
        Batch batch=new Batch();
        batch.setId(1L);
        batch.setName("Batch01");
        batch.setStartDate(LocalDate.of(2022, 10, 10));
        batch.setEndDate(LocalDate.of(2022, 10, 10));
        batch.setBatchCourse(new Course());
        batch.setUserList(new ArrayList<>());
        return batch;   
    }   

     private Schedule getSchedule(){
        Schedule schedule=new Schedule();
        schedule.setId(1L);
        schedule.setDate(LocalDate.of(2022, 10, 10));
        schedule.setUserScheduleList(new ArrayList<>());
        return schedule;
    }

    private User getUser(){
        User user = new User();
        user.setId("usr001");
        user.setName("testName");
        user.setPassword("password");
        user.setRole("admin");
        user.setBatchId("batch01");
        
        user.setBatchList(new ArrayList<>());

        return user;
    }
}
