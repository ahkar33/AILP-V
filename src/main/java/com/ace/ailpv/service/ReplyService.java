package com.ace.ailpv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Reply;
import com.ace.ailpv.repository.ReplyRepository;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    public void addReply(Reply reply) {
        replyRepository.save(reply);
    }

}
