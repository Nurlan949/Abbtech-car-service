package com.abbtech.controller;

import com.abbtech.service.CurrentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/current")
public class CurrentController {
    private final CurrentService currentService;

    public CurrentController(CurrentService currentService) {
        this.currentService = currentService;
    }

    @GetMapping("/rate")
    public String getCurrentRate(@RequestParam String date, @RequestParam String code) {
        return currentService.getRate(date,code);
    }
}
