package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

    List<Comment> findByCommentBatch_IdAndCommentVideoIdOrderByIdDesc(Long batchId, Long videoId); 

}
