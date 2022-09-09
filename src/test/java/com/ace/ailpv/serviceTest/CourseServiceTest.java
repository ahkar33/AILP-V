package com.ace.ailpv.serviceTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.ace.ailpv.repository.VideoRepository;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.CourseService;
import com.ace.ailpv.service.ExamService;
import com.ace.ailpv.service.FileService;
import com.ace.ailpv.service.VideoService;

import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;

@SpringBootTest
public class CourseServiceTest {

    @InjectMocks
    CourseService courseService;
    @Mock
    VideoRepository videoRepository;
    @Mock
    CourseRepository courseRepository;
    @Mock
    FileService fileService;
    @Mock
    BatchService batchService;
    @Mock
    ExamService examService;
    @Mock
    VideoService videoService;

    @Test
    public void addCourseTest() throws IllegalStateException, IOException, InputFormatException, EncoderException {
      Course course = getOneCourse(1L);
      when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
      courseService.addCourse(course);
     verify(courseRepository,times(1)).save(course);
    }

    // @Test
    // public void addVideoTest() throws IllegalStateException, IOException {
    //   Course course = getOneCourse(1L);
    //   Video video=(Video) getVideoList().toArray()[0];
    //   course.setVideos(new MultipartFile[]{new MockMultipartFile[]()});
    //   when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
    //   courseService.addCourse(course);
    //  verify(videoRepository,times(1)).save(video);
    // }


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
        Course course=getOneCourse(1L);
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
        Course course=getOneCourse(1L);
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
        when(examService.getExamListByCourseId(1L)).thenReturn(examList);
        courseService.deleteCourseById(1L, "java");
        verify(courseRepository,times(1)).deleteById(1L);
    }

    @Test
    public void checkCourseNameTest(){
        String courseName="Java";
        when(courseRepository.existsByName(courseName)).thenReturn(true);
        Boolean hasCoursename = courseService.checkCourseName(courseName);
        assertTrue(hasCoursename);
        verify(courseRepository, times(1)).existsByName("Java");
    }

    @Test
    public void updateCourseTest(){
        Course course=getOneCourse(1L);
        courseService.updateCourse(course);
        verify(courseRepository, times(1)).save(course);    

    }

    @Test
    public void getCourseCountTest(){
        courseService.getCourseCount();
        verify(courseRepository,times(1)).courseCount();
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

    private List<Batch>getBatchList(){
        Batch batch=new Batch();
        List<Batch>batchList=new ArrayList<>();
        batch.setId(1L);
        batch.setName("Batch01");
        batch.setStartDate(LocalDate.of(2022, 10, 10));
        batch.setEndDate(LocalDate.of(2022, 10, 10));
        batch.setBatchCourse(new Course());
        Batch batch1=new Batch();
        batch1.setId(1L);
        batch1.setName("Batch01");
        batch1.setStartDate(LocalDate.of(2022, 10, 10));
        batch1.setEndDate(LocalDate.of(2022, 10, 10));
        batch1.setBatchCourse(new Course());
        batchList.add(batch);
        batchList.add(batch1);
        return batchList;

    }

    private Course getOneCourse(Long courseId) {
        Course course1 = new Course();
        course1.setId(courseId);
        course1.setName("java");
        course1.setFee(100.00);
        course1.setDescription("asddf");
        course1.setVideos(new MultipartFile[] {});
        course1.setResources(new MultipartFile[] {});
        course1.setBatchList(new ArrayList<Batch>());
        course1.setVideoList(new ArrayList<Video>());
        course1.setResourceList(new ArrayList<Resource>());
        return course1;
    }

    private List<Course> getCourseList() {
        List<Course>courseList=new ArrayList<>();
        Course course1 = getOneCourse(1L);
        Course course2=getOneCourse(2L);
        courseList.add(course1);
        courseList.add(course2);
        return courseList;
    }
}