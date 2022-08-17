package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.UserRepository;
import com.ace.ailpv.service.UserService;

@SpringBootTest
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    //check login test need

    @Test
    public void addUserTest(){
        User user = getUserInfo();
        userService.addUser(user);
        verify(userRepository, times(1)).save(user);

    }

    @Test
    public void getAllUsersTest(){
        List<User> responseUserList = userService.getAllUsers();
        assertEquals(userRepository.findAll().size(), responseUserList.size());

    }

    @Test
    public void getAllStudentsTest(){
        List<User> studentList = getUserList().stream()
        .filter(user -> user.getRole().equals("student")).collect(Collectors.toList());
        assertEquals(studentList.size(), 1);
    }

    @Test
    public void getAllTeachersTest(){
        List<User> teacherList = getUserList().stream()
        .filter(user -> user.getRole().equals("teacher")).collect(Collectors.toList());
        assertEquals(teacherList.size(), 1);
    }

    @Test
    public void getUserByIdTest(){
        User user = getUserInfo();
        Mockito.when(userRepository.findById("usr001")).thenReturn(Optional.of(user));
        User actualResponse = userService.getUserById("usr001");
        assertEquals(actualResponse.getName(), user.getName());
    }

    @Test
    public void checkUserIdTest(){
        User user = getUserInfo();
        Mockito.when(userService.checkUserId(user.getId())).thenReturn(true);
        Mockito.when(userRepository.existsById(user.getId())).thenReturn(true);
    }

    @Test
    public void deleteUserByIdTest(){
        User user = getUserInfo();
        userService.deleteUserById(user.getId()); 
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void findUserByBatchIdTest(){
        List<User> userList = getUserList();
        Mockito.when(userRepository .findByBatchList_id(1L)).thenReturn(userList);
        Mockito.when(userService.findUserByBatchId(1L)).thenReturn(userList);
        
    }

    //need to test looping 
    @Test
    public void getStudentListByTeacherIdTest(){
        User User = getUserInfo();
        Set<Batch> batchList = User.getBatchList();

        Set<Batch> testbatchList = new HashSet<>();
        Batch batch1 = getBatchInfo();
        Batch batch2 = getBatchInfo();
        testbatchList.add(batch1);
        testbatchList.add(batch2);

        assertNotEquals(testbatchList.size(), batchList.size());

        List<User> userList = getUserList().stream()
        .filter(user -> user.getRole().equals("student")).collect(Collectors.toList());
        assertEquals(userList.size(), 1);
    }


    private User getUserInfo(){
        User user = new User();
        user.setId("usr001");
        user.setName("testName");
        user.setPassword("password");
        user.setRole("admin");
        user.setBatchId("batch01");
        
        user.setBatchList(new HashSet<>());

        return user;
    }

    private List<User> getUserList(){

        List<User> userList = new ArrayList<>();

        User user = new User();
        user.setId("usr001");
        user.setName("testName");
        user.setPassword("password");
        user.setRole("admin");
        user.setBatchId("batch01");
        user.setBatchList(new HashSet<>());

        User user1 = new User();
        user1.setId("usr001");
        user1.setName("testName");
        user1.setPassword("password");
        user1.setRole("student");
        user1.setBatchId("batch01");
        user1.setBatchList(new HashSet<>());

        User user2 = new User();
        user2.setId("usr001");
        user2.setName("testName");
        user2.setPassword("password");
        user2.setRole("teacher");
        user2.setBatchId("batch01");
        user2.setBatchList(new HashSet<>());

        userList.add(user);
        userList.add(user1);
        userList.add(user2);

        return userList;
    }

    private Batch getBatchInfo(){

        Set<User> userList = new HashSet<>();
        User user1 = getUserInfo();
        User user2 = getUserInfo();
        userList.add(user1);
        userList.add(user2);

        Batch batch = new Batch();
        batch.setId(1L);
        batch.setName("batchName");
        batch.setStartDate(LocalDate.of(2022,8,15));
        batch.setEndDate(LocalDate.of(2022,8,20));
        batch.setUserList(userList);
        Course batchCourse = getCourseInfo();
        batch.setBatchCourse(batchCourse);
        
        return batch;
        
    }

    private Course getCourseInfo(){

        Course course = new Course();
        course.setId(1L);
        course.setName("courseName");
        course.setFee(1000.0);
        course.setDescription("course description");
        Set<Batch> batchList = new HashSet<>();
        course.setBatchList(batchList);
        Set<Video> videoList = new HashSet<>();
        course.setVideoList(videoList);
        Set<Resource> resourceList = new HashSet<>();
        course.setResourceList(resourceList);

        return course;
    }

    
}
