package com.ace.ailpv.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
