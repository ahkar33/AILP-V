package com.ace.ailpv.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
public class User {

    @Id
    private String id;

    private String name;

    @JsonIgnore
    private String password;

    private String role;
    private String profile_pic;

    @Column(nullable = false, columnDefinition = "TINYINT(0)")
    private Boolean isMute;

    @ManyToMany
    // @JsonIgnore
    @JoinTable(name = "batch_has_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "batch_id"))
    List<Batch> batchList = new ArrayList<>();

    @Transient
    private String batchId;

    @Transient
    private String batchName;

    @Transient
    private String status = "Present";

    private Long readMessagesCount;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean enabled;

    @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, mappedBy = "messageUser")
    @JsonIgnore
    private List<Message> messageList = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, mappedBy = "commentUser")
    @JsonIgnore
    private List<Comment> userCommentList = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "user")
    @JsonIgnore
    private List<UserSchedule> userScheduleList = new ArrayList<>();

    private Long LastWatchVideoId;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "answerStudent")
    @JsonIgnore
    private List<AssignmentAnswer> userAnswerList = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "assignmentResultTeacher")
    @JsonIgnore
    private List<AssignmentResult> teacherAssignmentResult = new ArrayList<>();

    @Transient
    private List<Long> answerAssignmentIdList = new ArrayList<>();

}
