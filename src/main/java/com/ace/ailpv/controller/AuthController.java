package com.ace.ailpv.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BatchService batchService;

    @GetMapping("/login")
    public String setupLogin(ModelMap model) {
        model.addAttribute("user", new User());
        return "/USR-LGN-01";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, ModelMap model, HttpSession session) {
        if (userService.checkLogin(user.getId(), user.getPassword())) {
            User userInfo = userService.getUserById(user.getId());
            session.setAttribute("userInfo", userInfo);
            if (userInfo.getRole().equals("admin")) {
                // changed back later to admin dashboard
                return "redirect:/admin/course-table";
            } else if (userInfo.getRole().equals("teacher")) {
                // changed back later to teacher dashboard
                return "redirect:/teacher/student-table";
            } else {
                // changed back later to student home
                userInfo.setBatchId(userInfo.getBatchList().iterator().next().getId().toString());
                Batch userBatch = batchService.getBatchById(Long.parseLong(userInfo.getBatchId()));
                if (userBatch.getIsActive()) {
                    session.setAttribute("userInfo", userInfo);
                    return "redirect:/student/student-home";
                }
                String message = "Your Batch has been disabled";
                model.addAttribute("message", message);
                return "/USR-LGN-01";
            }
        }
        String message = "ID and Password do not match";
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "/USR-LGN-01";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo");
        session.invalidate();
        return "redirect:/auth/login";
    }

}
