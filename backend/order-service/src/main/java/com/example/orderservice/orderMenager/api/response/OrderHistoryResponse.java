package com.example.orderservice.orderMenager.api.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderHistoryResponse {
    private List<OrderHistory> orderHistoryList;
    private int maxPages;

}
