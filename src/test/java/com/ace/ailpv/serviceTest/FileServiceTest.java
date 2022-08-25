package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileServiceTest {

    // file created in temdir will be deleted after test
    @TempDir
    Path tempDir;

    @TempDir
    Path videoDir;

    @TempDir
    Path resourceDir;

    String courseFilePath = "D:\\OJT BATCH 4\\final project\\AILP-V\\src\\main\\resources\\static\\courses\\";

    @Test
    public void createFolderForCourseTest() {

        String courseName = "courseName";
        File pathDir = new File(courseFilePath + courseName);
        pathDir.delete();
        assertFalse(pathDir.exists());
        pathDir.mkdirs();
        assertTrue(pathDir.exists());

        File videoDir = new File(courseFilePath + courseName + "\\video");
        videoDir.delete();
        assertFalse(videoDir.exists());
        videoDir.mkdirs();
        assertTrue(videoDir.exists());

        File resourceDir = new File(courseFilePath + courseName + "\\resource");
        resourceDir.delete();
        assertFalse(resourceDir.exists());
        resourceDir.mkdirs();
        assertTrue(resourceDir.exists());

    }

    @Test
    public void createFileTest() throws IOException {
        Path filedir = tempDir.resolve("file.txt");
        Files.writeString(filedir, "test file");
        assertTrue( Files.exists(filedir), "File should exist");

    }

    @Test
    public void deleteFolderTest(){
        File videoDir = new File(courseFilePath +"\\video");
        videoDir.delete();
        assertFalse(videoDir.exists(), "Folder should not exist");

    }

    @Test
    public void listFilesUsingJavaIOTest(){

    }

    @Test
    public void deleteFileTest()throws IOException{
        Path filedir = tempDir.resolve("file.txt");
        Files.writeString(filedir, "test file");
        Files.delete(filedir);
        assertTrue(Files.notExists(filedir), "file should not exist");

    }

    @Test
    public void renameDirTest(){
        
    }

}
