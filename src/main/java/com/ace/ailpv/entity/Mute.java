package com.ace.ailpv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Mute {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    
    @ManyToOne
	@JoinColumn(name = "batch_id", referencedColumnName = "id")
    private Batch muteBatch;

    @ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    private User muteUser;

    @Column(nullable = false, columnDefinition = "TINYINT(0)")
    private Boolean isMute;

    @Transient
    private Long batchId;

    @Transient
    private String userId;

}
