package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
   
    List<Message> findByMessageBatch_IdOrderByIdAsc(Long id);
    
}
