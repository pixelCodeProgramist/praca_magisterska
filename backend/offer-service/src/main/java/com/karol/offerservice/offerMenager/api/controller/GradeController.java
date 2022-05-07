package com.karol.offerservice.offerMenager.api.controller;

import com.karol.offerservice.offerMenager.api.request.GradeRequest;
import com.karol.offerservice.offerMenager.api.response.ResponseView;
import com.karol.offerservice.offerMenager.business.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController()
@RequestMapping("/offer")
@AllArgsConstructor
@Validated
public class GradeController {
    private OfferService offerService;

    @PostMapping("/grade")
    public ResponseView getDetailUserById(@RequestBody @Valid GradeRequest gradeRequest, HttpServletRequest httpServletRequest) {
        offerService.grade(gradeRequest, httpServletRequest);
        return new ResponseView("Ocena została wysłana");
    }

    @GetMapping("/grade/canGrade/{productId}")
    public Boolean canGrade(@PathVariable("productId") Long productId, HttpServletRequest httpServletRequest) {
        return offerService.canGrade(productId, httpServletRequest);
    }
}
