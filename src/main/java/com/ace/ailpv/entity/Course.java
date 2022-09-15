package com.ace.ailpv.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String name;

    private Double fee;
    
    @Column(length = 1000)
    private String description;

    @Transient
    private MultipartFile videos[];

    @Transient
    private MultipartFile resources[];

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "batchCourse")
    @JsonIgnore
    List<Batch> batchList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videoCourse")
    @JsonIgnore
    List<Video> videoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resourceCourse")
    @JsonIgnore
    List<Resource> resourceList = new ArrayList<>();

}
