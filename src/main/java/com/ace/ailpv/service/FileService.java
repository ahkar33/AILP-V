package com.ace.ailpv.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    String courseFilePath = "D:\\ACE(OJT)\\AILP(V)\\AILP(V)\\AILP-V\\src\\main\\resources\\static\\courses\\";


    public void createFolderForCourse(String courseName) {
        File theDir = new File(courseFilePath + courseName);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        File videoFolder = new File(courseFilePath + courseName + "\\video");
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }
        File resourceFolder = new File(courseFilePath + courseName + "\\resource");
        if (!resourceFolder.exists()) {
            resourceFolder.mkdirs();
        }
    }

    public void createFile(MultipartFile file, String folderName) throws IllegalStateException, IOException {
        file.transferTo(
                new File(courseFilePath + folderName + "\\" + file.getOriginalFilename()));
    }

    public void deleteFolder(String folderName) throws IOException {
        File directory = new File(courseFilePath + folderName);
        FileUtils.deleteDirectory(directory);
    }

    public Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public boolean deleteFile(String fileName) throws IOException {
        boolean isDeleted = new File(fileName).delete();
        return isDeleted;
    }

    public String generateFileName(String fileExtension) {
        return String.join("", UUID.randomUUID().toString().split("-")) + "." + fileExtension;
    }

}