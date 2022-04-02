package com.karol.generalinformationservice.sectionMenager.api.controller;

import com.karol.generalinformationservice.sectionMenager.api.response.GeneralInfoView;
import com.karol.generalinformationservice.sectionMenager.api.response.ImageView;
import com.karol.generalinformationservice.sectionMenager.business.service.GeneralInformationService;
import com.karol.generalinformationservice.sectionMenager.business.service.ImageService;
import com.karol.generalinformationservice.sectionMenager.data.entity.ImageForSection;
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
    public List<ImageForSection> getPhotoLinks(@RequestParam("section") String section) {
        return imageService.getImagesForSection(section);
    }

    @PostMapping("/photo")
    public List<ImageView> getPhotos(@RequestBody List<ImageForSection> imageForSections) {
        return imageService.getImagesForUrls(imageForSections);
    }

    @GetMapping("/all")
    public GeneralInfoView getAllGeneralInformation() {
        return generalInformationService.getAllGeneralInfo();
    }



}
