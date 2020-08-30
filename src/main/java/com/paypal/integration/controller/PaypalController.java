package com.paypal.integration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/paypal")
@RestController
public class PaypalController {

    @GetMapping("/test")
    public String test() {
        return "It works";
    }

}
