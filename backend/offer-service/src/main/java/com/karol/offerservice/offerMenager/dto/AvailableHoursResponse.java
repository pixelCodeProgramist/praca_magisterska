package com.karol.offerservice.offerMenager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AvailableHoursResponse {
    private List<String> availableHours;
}
