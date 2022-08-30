package com.ace.ailpv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String replyText;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment replyComment;

    @ManyToOne
    @JsonIgnoreProperties("batchList")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User replyUser;

    @Column(name = "date_time")
    private String dateTime;

    @Transient
    private Long replyCommentId;

    @Transient
    private String replyUserId;

}
