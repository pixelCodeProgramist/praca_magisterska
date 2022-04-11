package com.karol.generalinformationservice.generalInformationMenager.api.controller;

import com.karol.generalinformationservice.generalInformationMenager.api.response.GeneralInfoView;
import com.karol.generalinformationservice.generalInformationMenager.api.response.ImageView;
import com.karol.generalinformationservice.generalInformationMenager.business.service.GeneralInformationService;
import com.karol.generalinformationservice.generalInformationMenager.business.service.ImageService;
import com.karol.generalinformationservice.generalInformationMenager.data.entity.ImageForSection;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping("/general-information")
@AllArgsConstructor
public class GeneralInformationController {

    private ImageService imageService;
    private GeneralInformationService generalInformationService;

    @GetMapping("/photo")
    public List<ImageView> getPhotoLinks(@RequestParam("section") String section) {
        List<ImageForSection> imageForSections = imageService.getImagesForSection(section);
        return imageService.getImagesForUrls(imageForSections);
    }

    @GetMapping("/all")
    public GeneralInfoView getAllGeneralInformation() {
        return generalInformationService.getAllGeneralInfo();
    }



}
