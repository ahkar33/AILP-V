package com.ace.ailpv.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    public void createFolderForCourse(String courseName) {
        File theDir = new File("C:\\Users\\Ahkar Toe Maw\\Documents\\AILP-V\\ailp-v\\src\\main\\resources\\static\\courses\\" + courseName);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        File videoFolder = new File("C:\\Users\\Ahkar Toe Maw\\Documents\\AILP-V\\ailp-v\\src\\main\\resources\\static\\courses\\" + courseName + "\\video");
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }
        File resourceFolder = new File("C:\\Users\\Ahkar Toe Maw\\Documents\\AILP-V\\ailp-v\\src\\main\\resources\\static\\courses\\" + courseName + "\\resource");
        if (!resourceFolder.exists()) {
            resourceFolder.mkdirs();
        }
    }


    public void createFile(MultipartFile file, String folderName) throws IllegalStateException, IOException {

        file.transferTo(
                new File(
                        "C:\\Users\\Ahkar Toe Maw\\Documents\\AILP-V\\ailp-v\\src\\main\\resources\\static\\courses\\"
                                + folderName + "\\"
                                + file.getOriginalFilename()));
    }

    public void  deleteFolder(String folderName) throws IOException {
                File directory = new File(
                "C:\\Users\\Ahkar Toe Maw\\Documents\\AILP-V\\ailp-v\\src\\main\\resources\\static\\courses\\" + folderName);
        FileUtils.deleteDirectory(directory);
    }

}