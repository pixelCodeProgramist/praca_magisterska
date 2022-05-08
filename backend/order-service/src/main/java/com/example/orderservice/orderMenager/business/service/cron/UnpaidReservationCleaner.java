package com.example.orderservice.orderMenager.business.service.cron;

import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.orderMenager.data.repository.UserOrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UnpaidReservationCleaner {
    private UserOrderRepo userOrderRepo;

    @Scheduled(cron = "* */30 * * * *")
    public void clear() {
//        List<UserOrder> userOrders = userOrderRepo.findByPaidIs(false);
//        Date now = new Date();
//        userOrders.removeIf(userOrder ->
//            now.after(userOrder.getTimeToPaid())
//        );


    }
}

