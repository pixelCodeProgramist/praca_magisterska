package com.karol.offerservice.offerMenager.business.service;

import com.karol.offerservice.offerMenager.business.exception.image.ImageNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;

@Service
@AllArgsConstructor
public class ImageService {
    private ResourceLoader resourceLoader;

    public byte[] getImagesForUrls(String url) {
        try {
            Resource resource = resourceLoader.getResource("classpath:static/" + url);
            File file = resource.getFile();
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return fileContent;
        } catch (Exception exception) {
            throw new ImageNotFoundException(url);
        }
    }
}
