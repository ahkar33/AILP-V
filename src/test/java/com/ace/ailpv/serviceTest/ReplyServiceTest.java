package com.ace.ailpv.serviceTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.Comment;
import com.ace.ailpv.entity.Reply;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.ReplyRepository;
import com.ace.ailpv.service.ReplyService;

@SpringBootTest
public class ReplyServiceTest {
    @Mock
    ReplyRepository replyRepository;

    @InjectMocks 
    ReplyService replyService;

    @Test
    public void addReplyTest(){
        Reply reply=getReply();
        replyService.addReply(reply);
        verify(replyRepository,times(1)).save(reply);
    }

    @Test
    public void deleteReplyById(){
        Reply reply=getReply();
        replyService.deleteReplyById(reply.getId());
        verify(replyRepository,times(1)).deleteById(reply.getId());
    }

    private Reply getReply(){
        Reply reply=new Reply();
        reply.setId(1L);
        reply.setReplyText("replyText");
        reply.setReplyComment(new Comment());
        reply.setReplyUser(new User());
        reply.setDateTime("10-10-2022");
        return reply;
    }
    
}
