package com.example.infinitiumassignment.controller;

import com.example.infinitiumassignment.model.SendRequest;
import com.example.infinitiumassignment.model.SendResponse;
import com.example.infinitiumassignment.service.MainService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    final MainService mainService;

    public MainController(MainService service) {
        this.mainService = service;
    }

    @PostMapping("/validatePayment")
    public SendResponse sendMoney(SendRequest request) throws Exception {
        return mainService.sendMoney(request);
    }
}
