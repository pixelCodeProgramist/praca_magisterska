package com.karol.generalinformationservice.generalInformationMenager.api.mapper;

import com.karol.generalinformationservice.generalInformationMenager.api.response.ImageView;
import com.karol.generalinformationservice.generalInformationMenager.data.entity.ImageForSection;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageMapper {
    public ImageView mapDataToResponse(ImageForSection imageForSection, byte[] image){
        return new ImageView().builder()
                .imageId(imageForSection.getImageForSectionId())
                .fileName(imageForSection.getFileName())
                .url(imageForSection.getUrl())
                .image(image)
                .build();
    }
}
