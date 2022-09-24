package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.FileExam;

public interface FileExamRepository extends JpaRepository<FileExam, Long>{

    Boolean existsByNameAndFileExamCourse_Id(String name, Long courseId);

    List<FileExam> findByFileExamCourse_Id(Long courseId);

}
