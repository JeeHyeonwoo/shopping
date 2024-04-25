package com.hyeonu.shopping.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Component
public class FileUtils {
    @Value("${uploadDir.main}")
    private String uploadDir;

    public String upload(MultipartFile file, String subFolderName) throws IOException {
        Long timeStamp = new Date().getTime();
        String path = uploadDir + "/" + subFolderName + "/" + file.getOriginalFilename() + timeStamp.toString();

        if (!file.isEmpty()) {
            file.transferTo(new File(path));
        }

        return path;
    }
}
