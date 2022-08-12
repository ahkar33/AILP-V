package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Student;
import com.ace.ailpv.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudentById(String id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentById(String id) {
        return studentRepository.findStudentById(id);
    }

    public Student getStudentByName(String name) {
        return studentRepository.findByName(name);
    }

}
