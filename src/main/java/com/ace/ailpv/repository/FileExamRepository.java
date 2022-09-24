package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.FileExam;

public interface FileExamRepository extends JpaRepository<FileExam, Long>{

    Boolean existsByNameAndFileExamCourse_Id(String name, Long courseId);

}
