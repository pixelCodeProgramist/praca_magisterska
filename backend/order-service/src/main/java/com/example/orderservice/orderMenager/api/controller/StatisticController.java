package com.example.orderservice.orderMenager.api.controller;

import com.example.orderservice.orderMenager.api.request.AprioriRequest;
import com.example.orderservice.orderMenager.business.service.StatisticService;
import com.example.orderservice.orderMenager.business.service.aprioriPackage.AssociationRule;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class StatisticController {

    private StatisticService statisticService;

    @PostMapping("/statistic")
    public List<AssociationRule> getStatistic(@Valid @RequestBody AprioriRequest aprioriRequest) {
        return statisticService.getStatistic(aprioriRequest);
    }

}
