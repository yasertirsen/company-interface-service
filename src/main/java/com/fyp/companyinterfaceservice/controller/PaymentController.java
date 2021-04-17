package com.fyp.companyinterfaceservice.controller;

import com.fyp.companyinterfaceservice.model.User;
import com.fyp.companyinterfaceservice.service.interfaces.PaymentService;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class PaymentController {

    private final PaymentService paymentService;
    @Value("${stripe.apiKey}")
    private String apiKey;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    @PostMapping("/creatCheckoutSession")
    public ResponseEntity<Object> createCheckoutSession(@RequestBody User user) {
        return paymentService.createCheckoutSession(user);
    }
}
