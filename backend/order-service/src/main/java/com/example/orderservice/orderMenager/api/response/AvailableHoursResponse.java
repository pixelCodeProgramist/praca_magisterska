package com.example.orderservice.orderMenager.api.response;

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
