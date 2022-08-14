package com.ace.ailpv.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToOne
    // @JsonBackReference
    @JoinColumn(name = "course_id", nullable = false)
    private Course batchCourse;

    // @Transient
    // private Long courseId;

    @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, mappedBy = "studentBatch")
    // @JsonManagedReference
    Set<Student> studentList = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, mappedBy = "batchList")
    // @JsonManagedReference
    Set<Teacher> teacherList = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST }, mappedBy = "batchList")
    // @JsonManagedReference
    Set<User> userList = new HashSet<>();

}