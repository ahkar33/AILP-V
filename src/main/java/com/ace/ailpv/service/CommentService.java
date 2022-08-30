package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Comment;
import com.ace.ailpv.repository.CommentRepository;

@Service
public class CommentService {
  
    @Autowired
    private CommentRepository commentRepository;    

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getCommentListByBatchIdAndVideoId(Long batchId, Long videoId) {
        return commentRepository.findByCommentBatch_IdAndCommentVideoIdOrderByIdDesc(batchId, videoId);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

}
