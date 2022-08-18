package com.ace.ailpv.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Message;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.MessageService;
import com.ace.ailpv.service.UserService;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "*")
public class MessageApi {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private BatchService batchService;

    @PostMapping("/addMessage")
    public void addMessage(@RequestBody Message message) {
        Batch batch = batchService.getBatchById(Long.parseLong(message.getBatchId()));
        User user = userService.getUserById(message.getUserId());
        LocalDateTime now = LocalDateTime.now();
        message.setDateTime(now);
        message.setMessageBatch(batch);
        message.setMessageUser(user);
        messageService.addMessage(message);
    }

    @GetMapping("/getMessagesByBatchId/{id}")
    public List<Message> getAllMessages(@PathVariable("id") String id) {
        long batchId = Long.parseLong(id);
        return messageService.getMessageListByBatchId(batchId);
    }

}
