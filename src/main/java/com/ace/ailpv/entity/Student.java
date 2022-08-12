package com.ace.ailpv.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    private String id;

    private String name;
    private String password;

    @ManyToOne
    // @JsonIgnoreProperties({"studentList", "teacherList"})
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch studentBatch;

    @Transient
    private String batchId;

}
