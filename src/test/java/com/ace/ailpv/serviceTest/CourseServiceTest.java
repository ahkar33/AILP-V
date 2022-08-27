package com.ace.ailpv.serviceTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Exam;
import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.CourseRepository;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.CourseService;
import com.ace.ailpv.service.ExamService;
import com.ace.ailpv.service.FileService;

@SpringBootTest
public class CourseServiceTest {
    @Mock
    CourseRepository courseRepository;
    @InjectMocks
    CourseService courseService;
    @Mock
    FileService fileService;
    @Mock
    BatchService batchService;
    @Mock
    ExamService examService;

    @Test
    public void addCourseTest() {
       // Course course = getOneCourse();
    
    }

    @Test
    public void getAllCoursesTest() {
        List<Course> list = getCourseList();
        when(courseRepository.findAll()).thenReturn(list);
        List<Course> courseList = courseService.getAllCourses();
        assertEquals(list.size(), courseList.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void getCourseByIdTest(){
        Course course=getOneCourse();
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        Course selectedCourse=courseService.getCourseById(course.getId());
        assertEquals(course.getName(), selectedCourse.getName());
        assertEquals(course.getFee(), selectedCourse.getFee());
        assertEquals(course.getDescription(), selectedCourse.getDescription());
        assertEquals(course.getVideos(), selectedCourse.getVideos());
        assertEquals(course.getResources(), selectedCourse.getResources());
        verify(courseRepository, times(1)).findById(1L);    
    }

    @Test
    public void getCourseByNameTest(){
        Course course=getOneCourse();
        when(courseRepository.findByName(course.getName())).thenReturn(course);
        Course selectedCourse=courseService.getCourseByName(course.getName());
        assertEquals(course.getName(), selectedCourse.getName());
        assertEquals(course.getFee(), selectedCourse.getFee());
        assertEquals(course.getDescription(), selectedCourse.getDescription());
        assertEquals(course.getVideos(), selectedCourse.getVideos());
        assertEquals(course.getResources(), selectedCourse.getResources());
        verify(courseRepository, times(1)).findByName("java");    
    }

    @Test
    public void deleteCourseByIdTest() throws IOException{
        List<Batch>batchList=getBatchList();
        List<Exam>examList=getExamList();
        when(batchService.getBatchesByCourseId(1L)).thenReturn(batchList);
        when(examService.getExamListByBatchId(1L)).thenReturn(examList);
        verify(courseRepository,times(1)).deleteById(1L);
    }

    @Test
    public void checkCourseNameTest(){
        List<Course> list = getCourseList();
        when(courseRepository.existsByName("Java"));
        Boolean courseList = courseService.checkCourseName("Java");
        assertEquals(list,courseList);
        verify(courseRepository, times(1)).existsByName("java");
    }

    @Test
    public void updateCourseTest(){
        Course course=getOneCourse();
        courseService.updateCourse(course);
        verify(courseRepository, times(1)).save(course);    

    }

    private List<Exam>getExamList(){
        List<Exam>examList=new ArrayList<>();
        Exam exam=new Exam();
        exam.setId(1L);
        exam.setName("mid term");
        exam.setQuestionList(new ArrayList<>());
        exam.setFullMark(100.00);
        exam.setExamCourse(new Course());
        examList.add(exam);
        return examList;
    }
    private List<Video>getVideoList(){
        List<Video>videoList=new ArrayList<>();
        Video video=new Video();
        video.setId(1L);
        video.setName("Java");
        video.setVideoCourse(getOneCourse());
        videoList.add(video);
        return videoList;
    }

    private List<Resource>getResourceList(){
        List<Resource>resourseList=new ArrayList<>();
        Resource resource=new Resource();
        resource.setId(1L);
        resource.setName("ppt");
        resource.setResourceCourse(getOneCourse());
        resourseList.add(resource);
        return resourseList;
    }


    private List<Batch>getBatchList(){
        Batch batch=new Batch();
        List<Batch>batchList=new ArrayList<>();
        batch.setId(1L);
        batch.setName("Batch01");
        batch.setStartDate(LocalDate.of(2022, 10, 10));
        batch.setEndDate(LocalDate.of(2022, 10, 10));
        batch.setBatchCourse(getOneCourse());
        Batch batch1=new Batch();
        batch1.setId(1L);
        batch1.setName("Batch01");
        batch1.setStartDate(LocalDate.of(2022, 10, 10));
        batch1.setEndDate(LocalDate.of(2022, 10, 10));
        batch1.setBatchCourse(getOneCourse());
        batchList.add(batch);
        batchList.add(batch1);
        return batchList;

    }

    private Course getOneCourse() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("java");
        course1.setFee(100.00);
        course1.setDescription("asddf");
        course1.setVideos(new MultipartFile[] {});
        course1.setResources(new MultipartFile[] {});
        course1.setBatchList(getBatchList());
        course1.setVideoList(getVideoList());
        course1.setResourceList(getResourceList());
        return course1;
    }

    private List<Course> getCourseList() {
        List<Course>courseList=new ArrayList<>();
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("java");
        course1.setFee(100.00);
        course1.setDescription("asddf");
        course1.setVideos(new MultipartFile[] {});
        course1.setResources(new MultipartFile[] {});
        course1.setBatchList(getBatchList());
        course1.setVideoList(getVideoList());
        course1.setResourceList(getResourceList());
        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("java");
        course2.setFee(100.00);
        course2.setDescription("asddf");
        course2.setVideos(new MultipartFile[] {});
        course2.setResources(new MultipartFile[] {});
        course2.setBatchList(getBatchList());
        course2.setVideoList(getVideoList());
        course2.setResourceList(getResourceList());
        courseList.add(course1);
        courseList.add(course2);
        return courseList;
    }
}