package com.karol.offerservice.offerMenager.business.service;

import com.karol.offerservice.offerMenager.business.exception.image.ImageNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;

@Service
@AllArgsConstructor
public class ImageService {
    private ResourceLoader resourceLoader;

    public byte[] getImagesForUrls(String url, boolean isTest) {
        try {
            if(!isTest) {
                String userDirectory="../offer-service/src/main/resources/static/";


                File file = new File(userDirectory+url);;
                byte[] fileContent = Files.readAllBytes(file.toPath());
                return fileContent;
            }
           return new byte[20];
        } catch (Exception exception) {
            throw new ImageNotFoundException(url);
        }
    }
}
