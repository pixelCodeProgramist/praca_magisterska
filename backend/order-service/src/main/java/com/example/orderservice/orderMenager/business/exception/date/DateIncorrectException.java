package com.example.orderservice.orderMenager.business.exception.date;

import java.util.Date;

public class DateIncorrectException extends RuntimeException {
    public DateIncorrectException(Date start, Date end) {
        super("End date "+end+" cannot be earlier then start date "+start);
    }
}
