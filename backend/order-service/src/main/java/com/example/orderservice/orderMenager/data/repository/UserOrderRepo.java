package com.example.orderservice.orderMenager.data.repository;

import com.example.orderservice.orderMenager.data.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserOrderRepo extends JpaRepository<UserOrder, Long> {
    @Query(value = "SELECT * FROM user_order o WHERE cast(begin_order as date) between :date and :date", nativeQuery = true)
    List<UserOrder> getUserOrderByDate(@Param("date") String date);

    List<UserOrder> findByBeginOrderBetween(Date from, Date to);
    List<UserOrder> findByPaidFalse();
}
