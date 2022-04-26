package com.example.orderservice.orderMenager.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class TestController {

    @GetMapping("/testPrivate")
    public String test1()
    {
        return "private";
    }

    @GetMapping("/testPublic")
    public String test2()
    {
        return "public";
    }


}
