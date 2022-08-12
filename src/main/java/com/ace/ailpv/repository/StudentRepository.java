package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ace.ailpv.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

    // @Query("SELECT s FROM Student s WHERE s.id=:id")
    // Student findStudentById(@Param("id") String id);

    @Query(value = "SELECT id, name, password, batch_id FROM student WHERE id=?1", nativeQuery = true)
    Student findStudentById(String id);

    Student findByName(String name);

}
