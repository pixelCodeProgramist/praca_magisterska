package com.example.orderservice.orderMenager.api.request;

import com.example.orderservice.orderMenager.data.entity.UserOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class OrderNameProductWithOrderIdRequest extends OrderNameProductRequest{
    private UserOrder userOrder;
    public OrderNameProductWithOrderIdRequest(Long bikeId, Long accessoryId, UserOrder userOrder) {
        super(bikeId, accessoryId);
        this.userOrder = userOrder;
    }
}
