package com.ace.ailpv.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String answerFile;
    private LocalDateTime submitTime;
    private String score;
    private String comment;
    private String studentName;
    private String teacherId;
    private Long questionFileId;

    @ManyToOne
    @JoinColumn(name="assignment_id", referencedColumnName = "id")
    private Assignment assignment;

    
}