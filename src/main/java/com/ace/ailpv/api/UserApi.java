package com.ace.ailpv.api;

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
import com.ace.ailpv.entity.Mute;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.MuteService;
import com.ace.ailpv.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private MuteService muteService;

    @Autowired
    private BatchService batchService;

    @GetMapping("/getUserIdList")
    public List<String> getUserIdList() {
        return userService.getUserIdList();
    }

    @GetMapping("/getUserList")
    public List<User> getUserList() {
        return userService.getAllUsers();
    }

    @GetMapping("/getMuteByBatchIdandUserId/{batchId}/{userId}")
    public Mute getMute(@PathVariable("batchId") Long batchId, @PathVariable("userId") String userId) {
        return muteService.getMuteByBatchIdAndUserId(batchId, userId);
    }

    @PostMapping("/toggleMute")
    public void toggleMute(@RequestBody Mute mute) {

        Mute resMute = muteService.getMuteByBatchIdAndUserId(mute.getBatchId(), mute.getUserId());
        User resUser = userService.getUserById(mute.getUserId());
        Batch resBatch = batchService.getBatchById(mute.getBatchId());

        if (resMute == null) {
            Mute reqMute = new Mute();
            reqMute.setMuteBatch(resBatch);
            reqMute.setMuteUser(resUser);
            reqMute.setIsMute(mute.getIsMute());
            muteService.addMute(reqMute);
        } else {
           resMute.setIsMute(mute.getIsMute()); 
           muteService.addMute(resMute);
        }

    }

    @GetMapping("/getUserReadMessagesCountById/{userId}")
    public Long getUserReadMessagesCount(@PathVariable("userId") String userID) {
        return userService.getUserReadMessagesCountById(userID);
    }

}
