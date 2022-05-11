package com.example.orderservice.orderMenager.business.service;


import com.example.orderservice.orderMenager.business.exception.image.ImageNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageService {
    public byte[] getImagesForUrls(String url) {

        try {
            String fileName = UUID.randomUUID().toString();
            String userDirectory = System.getProperty("user.dir");
            String[] parts = userDirectory.split("/");
            userDirectory = "";
            for (int j = 0; j < parts.length - 1; j++)
                userDirectory += parts[j] + "/";


            File file = new File(userDirectory + "order-service/src/main/resources/static/" + url);

            return Files.readAllBytes(file.toPath());

        } catch (Exception exception) {
            throw new ImageNotFoundException(url);
        }


    }
}