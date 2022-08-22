package com.ace.ailpv.service;

import java.io.IOException;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileValidationService {

    public final String[] allowResourceExtensionList = { "pdf", "png", "jpeg", "jpg" };
    public final String[] allowResourceMimeList = { "application/pdf", "image/png", "image/jpeg", "image/jpg" };

    public final String[] allowPictureExtensionList = { "png", "jpeg", "jpg", "jfif" };
    public final String[] allowPictureMimeList = { "image/png", "image/jpeg", "image/jpg", "image/jfif" };

    public String getExtension(String filename) {

        int index = filename.lastIndexOf(".");

        return filename.substring(index + 1);
    }

    public boolean isLimitExceed(Long fileSize) {
        if (fileSize <= 2097152)
            return false;
        return true;
    }

    public String getContentType(MultipartFile file, String filenameWithExtension) throws IOException {
        TikaConfig config = TikaConfig.getDefaultConfig();
        Detector detector = config.getDetector();
        TikaInputStream stream = TikaInputStream.get(file.getInputStream());
        Metadata metadata = new Metadata();
        metadata.add(TikaCoreProperties.RESOURCE_NAME_KEY, filenameWithExtension);

        return detector.detect(stream, metadata).toString();
    }
}
