package com.karol.offerservice.offerMenager.business.service;

import com.karol.offerservice.offerMenager.business.exception.image.ImageNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageService {
    private ResourceLoader resourceLoader;

    public byte[] getImagesForUrls(String url) {
        try {
            String userDirectory="../offer-service/src/main/resources/static/";


            File file = new File(userDirectory+url);;
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return fileContent;
        } catch (Exception exception) {
            throw new ImageNotFoundException(url);
        }
    }
}
