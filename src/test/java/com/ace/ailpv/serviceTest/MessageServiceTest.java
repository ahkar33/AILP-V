package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Message;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.MessageRepository;
import com.ace.ailpv.service.MessageService;

@SpringBootTest
public class MessageServiceTest {
    
    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    MessageService messageService;

    @Test 
    public void addMessageTest(){
        Message msg=getMessage();
        messageService.addMessage(msg);
        verify(messageRepository,times(1)).save(msg);
    }

    @Test
    public void getMessageByIdTest(){
        Message msg=getMessage();
        when(messageRepository.findById(msg.getId())).thenReturn(Optional.of(msg));
        Message msgActual=messageService.getMessageById(msg.getId());
        assertEquals(msg.getMessage(), msgActual.getMessage());
        assertEquals(msg.getBatchId(), msgActual.getBatchId());
        assertEquals(msg.getUserId(), msgActual.getUserId());
        assertEquals(msg.getDateTime(), msgActual.getDateTime());
        verify(messageRepository,times(1)).findById(msg.getId());
    }

    @Test
    public void deleteMessageTest(){
        Message msg=getMessage();
        messageService.deleteMessage(msg.getId());
        verify(messageRepository,times(1)).deleteById(msg.getId());
    }

    @Test
    public void getMessageListByBatchIdTest(){
        List<Message>msgList=getMessageList();
        when(messageRepository.findByMessageBatch_IdOrderByIdAsc(1L)).thenReturn(msgList);
        List<Message>msgListActual=messageService.getMessageListByBatchId(1L);
        assertEquals(msgList.size(), msgListActual.size());
        verify(messageRepository,times(1)).findByMessageBatch_IdOrderByIdAsc(1L);

    }

    @Test
    public void getLastInsertedIdTest(){
        messageService.getLastInsertedId();
        verify(messageRepository,times(1)).findLastId();
    }

    @Test
    public void countMessagesByBatchIdTest(){
        Message msg=getMessage();
        messageService.countMessagesByBatchId(msg.getId());
        verify(messageRepository,times(1)).countMessagesByBatchId(msg.getId());
    }



    private Message getMessage(){
        Message message=new Message();
        message.setId(1L);
        message.setMessage("Something");
        message.setDateTime("");
        message.setMessageUser(new User());
        message.setMessageBatch(new Batch());
        return message;
    }
     private List<Message>getMessageList(){
        List<Message>msgList=new ArrayList<>();
        Message message=new Message();
        message.setId(1L);
        message.setMessage("Something");
        message.setDateTime("");
        message.setMessageUser(new User());
        message.setMessageBatch(new Batch());
        msgList.add(message);
        return msgList;
    }
}
