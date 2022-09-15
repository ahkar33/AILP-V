package com.ace.ailpv.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String commentText;

    @ManyToOne
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video commentVideo;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User commentUser;

    @ManyToOne
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    private Batch commentBatch;

    private String dateTime;

    @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, mappedBy = "replyComment")
    private List<Reply> replyList = new ArrayList<>();

    @Transient
    private String userId;

    @Transient
    private Long videoId;

    @Transient
    private Long batchId;

    @Transient
    private Boolean isReplyShow = false;

}