package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ace.ailpv.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
   
    List<Message> findByMessageBatch_IdOrderByIdAsc(Long id);
   
    // @Query("SELECT m.id FROM Message m")
    // Long findLastId();

    @Query(value = "SELECT id FROM message ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Long findLastId();

    @Query(value = "SELECT count(message.id) FROM message WHERE message.batch_id = ?1", nativeQuery = true)
    Long countMessagesByBatchId(Long batchId); 

}
