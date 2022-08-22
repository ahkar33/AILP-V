package com.ace.ailpv.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String password;
    private String role;
    private String profile_pic;

    @Column(nullable = false, columnDefinition = "TINYINT(0)")
    private Boolean isMute;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "batch_has_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "batch_id"))
    List<Batch> batchList = new ArrayList<>(); 

    @Transient
    private String batchId;

    @Transient
    private String batchName;

    @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, mappedBy = "messageUser")
    @JsonIgnore
    private Set<Message> messageList = new HashSet<>();

}
