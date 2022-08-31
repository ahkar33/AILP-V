package com.ace.ailpv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Message;
import com.ace.ailpv.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void addMessage(Message message) {
        messageRepository.save(message);
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getMessageListByBatchId(Long id) {
        return messageRepository.findByMessageBatch_IdOrderByIdAsc(id);
    }

    public Long getLastInsertedId() {
        return messageRepository.findLastId();
    }

    public Long countMessagesByBatchId(Long batchId) {
        return messageRepository.countMessagesByBatchId(batchId);
    }

}
