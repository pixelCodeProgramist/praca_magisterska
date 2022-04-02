package com.karol.generalinformationservice.sectionMenager.business.service;


import com.karol.generalinformationservice.sectionMenager.api.mapper.ImageMapper;
import com.karol.generalinformationservice.sectionMenager.api.response.ImageView;
import com.karol.generalinformationservice.sectionMenager.business.exception.ImageError;
import com.karol.generalinformationservice.sectionMenager.business.exception.ImageException;
import com.karol.generalinformationservice.sectionMenager.data.entity.ImageForSection;
import com.karol.generalinformationservice.sectionMenager.data.repository.ImageForSectionRepo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {
    private ImageForSectionRepo imageForSectionRepo;

    private ResourceLoader resourceLoader;

    public List<ImageForSection> getImagesForSection(String name) {
        if(StringUtils.isEmpty(name) || StringUtils.isBlank(name))
            throw new ImageException(ImageError.MAIN_SECTION_NULL_EMPTY_BLANK_EXCEPTION);

        return imageForSectionRepo.findAllBySectionName(name);
    }

    public List<ImageView> getImagesForUrls(List<ImageForSection> imageForSections) {
        List<ImageView> imageViews = new ArrayList<>();
        for(int i=0;i<imageForSections.size();i++){
            try {
                Resource resource = resourceLoader.getResource("classpath:static/"+imageForSections.get(i).getUrl());
                File file = resource.getFile();
                byte[] fileContent = Files.readAllBytes(file.toPath());
                ImageView imageView = ImageMapper.mapDataToResponse(imageForSections.get(i), fileContent);
                imageViews.add(imageView);
            }catch (Exception exception) {
                throw new ImageException(ImageError.IMAGE_NOT_FOUND_EXCEPTION);
            }

        }
        return imageViews;
    }
}
