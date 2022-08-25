package com.ace.ailpv.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.FileService;
import com.ace.ailpv.service.FileValidationService;
import com.ace.ailpv.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends Thread {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    FileValidationService fileValidationService;

    @Autowired
    FileService fileService;

    @Autowired
    UserService userService;

    @Autowired
    BatchService batchService;

    @GetMapping("/profile")
    public String getProfile(HttpServletRequest request, ModelMap model) {

        String batches = "";

        String uid = (String) request.getSession(false).getAttribute("uid");
        User user = userService.getUserById(uid);

        if (user.getBatchList().size() > 0) {
            if (user.getBatchList().size() > 1) {
                for (Batch b : user.getBatchList()) {
                    batches += b.getName() + ", ";
                }
                batches = batches.substring(0, batches.lastIndexOf(","));
            } else {
                batches = user.getBatchList().iterator().next().getName();
            }
            model.addAttribute("batchName", batches);
        }

        if (user.getRole().equals("ROLE_ADMIN")) {
            return "/admin/ADM-PRF-16";
        } else if (user.getRole().equals("ROLE_TEACHER")) {
            return "/teacher/TCH-PRF-08";
        } else {
            return "/student/STU-PRF-08";
        }
    }

    @PostMapping("/change-name")
    public String changeName(@RequestParam("name") String name, HttpServletRequest request) {
        String uid = (String) request.getSession(false).getAttribute("uid");
        userService.updateByUserName(name, uid);
        request.getSession(false).setAttribute("name", name);
        return "redirect:/user/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
            HttpServletRequest request) {
        String uid = (String) request.getSession(false).getAttribute("uid");
        if (passwordEncoder.matches(oldPassword, userService.getUserById(uid).getPassword())) {
            if (newPassword.equals(confirmPassword)) {
                userService.updatePasswordByUserId(passwordEncoder.encode(newPassword), uid);
                System.out.println("ok");
            } else {
                System.out.println("Password Unmatched!");
            }
        } else {
            System.out.println("Invalid Password!");
        }
        return "redirect:/user/profile";

    }

    @PostMapping("/upload")
    public String uploadProfilePic(@RequestParam("profile_pic") MultipartFile profile_pic, HttpServletRequest request)
            throws IOException, InterruptedException {

        
        String profilePath = "D:\\ACE(OJT)\\AILP(V)\\AILP(V)\\AILP-V\\src\\main\\resources\\static\\profile_pics\\";

        boolean isValidFile = false;
        String randomName = fileService
                .generateFileName(fileValidationService.getExtension(profile_pic.getOriginalFilename()));

        if (!fileValidationService.isLimitExceed(profile_pic.getSize())) {

            String fileExtension = fileValidationService.getExtension(profile_pic.getOriginalFilename());
            for (String ext : fileValidationService.allowPictureExtensionList) {
                if (fileExtension.equals(ext)) {
                    String fileMimeType = fileValidationService.getContentType(profile_pic,
                            profile_pic.getOriginalFilename());

                    for (String mimeType : fileValidationService.allowPictureMimeList) {
                        if (fileMimeType.equals(mimeType)) {
                            isValidFile = true;
                        }
                    }
                }
            }
        }

        if (isValidFile) {
            profile_pic.transferTo(new File(profilePath + randomName));

            HttpSession session = request.getSession(false);
            String uid = (String) session.getAttribute("uid");
            userService.updatePictureByUserId(randomName, uid);

            if (!session.getAttribute("profile_pic").equals("profile.png")) {
                fileService.deleteFile(profilePath + session.getAttribute("profile_pic"));
            }
            session.setAttribute("profile_pic", randomName);

        } else {
            System.out.println("Invalid file!");
        }
        Thread.sleep(1500);
        return "redirect:/user/profile";
    }
}
