package com.ace.ailpv.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

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
public class FileExamAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examAnswerFileName;

    @Transient
    private MultipartFile examAnswerFile;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User examAnswerStudent;

    @ManyToOne
    @JoinColumn(name = "batch_has_file_exam_id", referencedColumnName = "id")
    private BatchHasFileExam batchHasFileExam;

    @Column(columnDefinition = "TINYINT(0)")
    private Boolean isGraded;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "examResultAnswer")
    private FileExamResult fileExamAnswerResult;

}