package com.ace.ailpv.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
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
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    @Transient
    private MultipartFile assignmentFile[];

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignment")
    @JsonIgnore
    List<AssignmentFile> assignmentFileList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "")
    @JsonIgnore
    List<AssignmentAnswer> answerList = new ArrayList<>();
}
