package com.ace.ailpv.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        message.setMessageBatch(batch);
        message.setMessageUser(user);
        messageService.addMessage(message);
    }

    @GetMapping("/getMessagesByBatchId/{id}")
    public List<Message> getAllMessages(@PathVariable("id") String id) {
        long batchId = Long.parseLong(id);
        return messageService.getMessageListByBatchId(batchId);
    }

    @GetMapping("/lastInsertedId")
    public Long getLastInsertedId() {
        return messageService.getLastInsertedId();
    }

    @GetMapping("/countMessagesByBatchId/{id}")
    public Long getMessagesByBatchId(@PathVariable("id") Long batchId) {
        return messageService.countMessagesByBatchId(batchId);
    }

    @PostMapping("/sendReadMessagesCount")
    public void sendOldMessagesCount(@RequestParam("count") Long count, HttpSession session) {
        String userId = (String) session.getAttribute("uid");
        User user = userService.getUserById(userId);
        user.setReadMessagesCount(count);
        userService.addUser(user);
    }

    @GetMapping("/deleteMessageById/{id}")
    public void deleteMessageById(@PathVariable("id") Long id) {
        messageService.deleteMessage(id);
    }

}