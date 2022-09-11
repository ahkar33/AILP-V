package com.ace.ailpv.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double fullMark;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    private List<Question> questionList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Course examCourse;

    @Transient
    private String courseId;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "bheExam")
    @JsonIgnore
    private List<BatchHasExam> batchHasExamList = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "sheExam")
    @JsonIgnore
    private List<StudentHasExam> studentHasExamList = new ArrayList<>();

}