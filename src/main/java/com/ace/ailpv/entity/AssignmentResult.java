package com.ace.ailpv.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private String mark;

    @OneToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private AssignmentAnswer assignmentResultAnswer;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private User assignmentResultTeacher;

}
