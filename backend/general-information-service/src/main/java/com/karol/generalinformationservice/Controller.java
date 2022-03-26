package com.karol.generalinformationservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/general-information")
public class Controller {
    @GetMapping("/getAll")
    public List<String> getList() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("3");
        return strings;
    }
}
