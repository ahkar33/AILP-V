package com.ace.ailpv.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class StudentReport {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
  
    private String studentId;
    private String name;
    private String batch;
    private String attendancePercentage;

}
