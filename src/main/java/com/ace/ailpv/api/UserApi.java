package com.ace.ailpv.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserApi {
  
    @Autowired
    private UserService userService;    

    @GetMapping("/getUserIdList")
    public List<String> getUserIdList() {
        return userService.getUserIdList();
    }

    @GetMapping("/getUserList")
    public List<User> getUserList() {
        return userService.getAllUsers();
    }
    
    @PostMapping("/toggleMute")
    public void toggleMute(@RequestBody User user) {
        User resUser = userService.getUserById(user.getId());
        resUser.setIsMute(user.getIsMute());
        userService.addUser(resUser);
    }

}
