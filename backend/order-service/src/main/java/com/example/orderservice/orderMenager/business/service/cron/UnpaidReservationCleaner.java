package com.example.orderservice.orderMenager.business.service.cron;

import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.orderMenager.data.repository.UserOrderRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Getter
public class UnpaidReservationCleaner {
    private UserOrderRepo userOrderRepo;

    @Scheduled(cron = "*/30 * * * * *")
    public void clear() {
        List<UserOrder> userOrders = userOrderRepo.findByPaidFalse();

        Date now = new Date();
        userOrders = userOrders.stream().filter(userOrder ->
           now.after(userOrder.getTimeToPaid())
        ).collect(Collectors.toList());

        userOrderRepo.deleteAll(userOrders);



    }
}

