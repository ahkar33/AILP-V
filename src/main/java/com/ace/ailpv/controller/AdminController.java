package com.ace.ailpv.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.CourseService;
import com.ace.ailpv.service.ExamService;
import com.ace.ailpv.service.FileService;
import com.ace.ailpv.service.UserService;
import com.ace.ailpv.service.VideoService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private FileService fileService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    String path = "D:\\ACE(OJT)\\AILP(V)\\AILP(V)\\AILP-V\\src\\main\\resources\\static\\courses\\";

    @GetMapping("/course-table")
    public String setupCourseTable(ModelMap model) {
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("course", new Course());
        return "/admin/ADM-COU-04";
    }

    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("course") Course course, RedirectAttributes redirectAttrs)
            throws IllegalStateException, IOException {
        if (courseService.checkCourseName(course.getName())) {
            redirectAttrs.addFlashAttribute("msg", "course name already exists");
            return "redirect:/admin/course-table";
        }
        courseService.addCourse(course);
        return "redirect:/admin/course-table";
    }

    @GetMapping("/editCourse/{id}")
    public String editCourse(@PathVariable("id") Long id, ModelMap model) {
        Course course = courseService.getCourseById(id);
        // Set<String> videoList = fileService.listFilesUsingJavaIO(path +
        // course.getName() + "\\video");
        // List<Video> videoList = videoService.getVideoByCourseId(String.valueOf(id));
        List<Video> videoList = videoService.getVideoByCourseId(id);

        model.addAttribute("videoList", videoList);
        model.addAttribute("course", course);
        return "/admin/ADM-VTB-14";
    }

    @GetMapping("/deleteFile/{vidId}/{courseName}")
    public String deleteFile(@PathVariable("vidId") String vidId, @PathVariable("courseName") String courseName,
            ModelMap model) throws IOException {

        Video videoName = videoService.getVideoById(Long.parseLong(vidId));
        Course course = courseService.getCourseByName(courseName);

        // boolean isDeleted =
        fileService.deleteFile(path + courseName + "\\video\\" + videoName.getName());
        videoService.deleteVideoById(Long.parseLong(vidId));
        // add data again

        // Set<String> videoList = fileService.listFilesUsingJavaIO(path +
        // course.getName() + "\\video");
        List<Video> videoList = videoService.getAllVideos();
        model.addAttribute("videoList", videoList);
        model.addAttribute("course", courseName);

        model.addAttribute("msg", "Deleted!!");
        return "redirect:/admin/editCourse/" + course.getId();
    }

    // this is where you fix
    @PostMapping("/uploadCourseVideo")
    public String uploadCourseVideo(ModelMap modal, @RequestParam("file") MultipartFile[] files,
            @RequestParam("courseId") String courseId) throws IllegalStateException, IOException {
        String courseName = courseService.getCourseById(Long.parseLong(courseId)).getName();
        Course course = courseService.getCourseById(Long.parseLong(courseId));
        List<Video> videoList = videoService.getVideoByCourseId(Long.parseLong(courseId));
        for (MultipartFile file : files) {
            fileService.createFile(file, courseName + "\\video");
            if (videoList.size() > 0) {
                for (Video video : videoList) {
                    System.out.println(video.getName());
                    System.out.println(file.getOriginalFilename());
                    if (!video.getName().equals(file.getOriginalFilename())) {
                        Video newVideo = new Video();
                        newVideo.setName(file.getOriginalFilename());
                        newVideo.setCourseId(course);
                        videoService.addVideo(newVideo);
                    }
                }
            } else {
                Video newVideo = new Video();
                newVideo.setName(file.getOriginalFilename());
                newVideo.setCourseId(course);
                videoService.addVideo(newVideo);
            }

        }

        return "redirect:/admin/editCourse/" + courseId;
    }

    @GetMapping("/deleteCourse/{id}/{courseName}")
    public String deleteCourse(@PathVariable("id") Long id, @PathVariable("courseName") String courseName)
            throws IOException {
        courseService.deleteCourseById(id, courseName);
        return "redirect:/admin/course-table";
    }

    @GetMapping("/exam-table")
    public String setupExamTable(ModelMap model) {
        model.addAttribute("examList", examService.getAllExams());
        return "/admin/ADM-ETB-05";
    }

    @GetMapping("/create-exam")
    public String setupCreateExam(ModelMap model) {
        model.addAttribute("courseList", courseService.getAllCourses());
        return "/admin/ADM-CRE-06";
    }

    @GetMapping("/batch-table")
    public String setupBatchTable(ModelMap model) {
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("batch", new Batch());
        model.addAttribute("editBatch", new Batch());
        model.addAttribute("batchList", batchService.getAllBatches());
        return "/admin/ADM-BTB-05";
    }

    @PostMapping("/addBatch")
    public String addBatch(@ModelAttribute("batch") Batch batch) {
        batchService.addBatch(batch);
        return "redirect:/admin/batch-table";
    }

    @GetMapping("/deleteBatch/{id}")
    public String deleteBatch(@PathVariable("id") Long id) {
        batchService.deleteBatchById(id);
        return "redirect:/admin/batch-table";
    }

    @GetMapping("/editBatch/{id}")
    public String setupEditBatch(@PathVariable("id") Long id, ModelMap model) {
        Batch batch = batchService.getBatchById(id);
        List<Course> courseList = courseService.getAllCourses();
        model.addAttribute("courseList", courseList);
        model.addAttribute("batch", batch);
        return "/admin/ADM-EDB-11";
    }

    @PostMapping("/editBatch")
    public String editBatch(@ModelAttribute("batch") Batch batch) {
        batchService.addBatch(batch);
        return "redirect:/admin/batch-table";
    }

    @GetMapping("/student-table")
    public String setupStudentTable(ModelMap model) {
        model.addAttribute("studentList", userService.getAllStudents());
        return "/admin/ADM-STB-08";
    }

    @GetMapping("/studentRegister")
    public String setupStudentRegister(ModelMap model) {
        model.addAttribute("batchList", batchService.getAllBatches());
        return "/admin/ADM-STG-07";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") String id)
            throws IOException {
        userService.deleteUserById(id);
        return "redirect:/admin/student-table";

    }

    @GetMapping("/editStudent/{id}")
    public String setupEditStudent(@PathVariable("id") String id, ModelMap model) {
        User student = userService.getUserById(id);
        model.addAttribute("batchList", batchService.getAllBatches());
        model.addAttribute("student", student);
        return "/admin/ADM-EDS-12";
    }

    @PostMapping("/editStudent")
    public String editStudent(@ModelAttribute("student") User student) {
        userService.addUser(student);
        return "redirect:/admin/student-table";
    }

    @GetMapping("/deleteExam/{id}")
    public String deleteExam(@PathVariable("id") Long id)
            throws IOException {
        examService.deleteExamById(id);
        return "redirect:/admin/exam-table";

    }

    @GetMapping("/teacher-table")
    public String setupTeacherTable(ModelMap model) {
        model.addAttribute("teacherList", userService.getAllTeachers());
        return "/admin/ADM-TTB-10";
    }

    @GetMapping("/teacherRegister")
    public String setupTeacherRegister(ModelMap model) {
        model.addAttribute("batchList", batchService.getAllBatches());
        return "/admin/ADM-TTG-09";
    }

    @GetMapping("/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return "redirect:/admin/teacher-table";
    }

    @GetMapping("/editTeacher/{id}")
    public String setupEditTeacher(@PathVariable("id") String id, ModelMap model) {
        User teacher = userService.getUserById(id);
        model.addAttribute("batchList", batchService.getAllBatches());
        model.addAttribute("teacher", teacher);
        return "/admin/ADM-EDT-13.html";
    }

    @PostMapping("/editTeacher")
    public String editTeacher(@ModelAttribute("teacher") User teacher) {
        userService.addUser(teacher);
        return "redirect:/admin/teacher-table";
    }

}