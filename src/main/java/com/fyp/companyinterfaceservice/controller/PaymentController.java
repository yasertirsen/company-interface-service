package com.fyp.companyinterfaceservice.controller;

import com.fyp.companyinterfaceservice.model.User;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    private final Gson gson;

    @Autowired
    public PaymentController(Gson gson) {
        this.gson = gson;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = "sk_test_51IR1L4GPvpv5kncLFykgOnqr7X9aqbdhCv5BLr8k4tHgWjuw2sUB4tL1JoGXvmb22ebkNYvCNEngws1tK4C7mXCq000ZjDogaq";
    }

    @PostMapping("/creatCheckoutSession")
    public ResponseEntity<Object> createCheckoutSession(@RequestBody User user) {
        SessionCreateParams params = new SessionCreateParams.Builder()
                .setSuccessUrl("http://localhost:4201/payment/success")
                .setCancelUrl("http://localhost:4201/payment/failure")
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .addLineItem(new SessionCreateParams.LineItem.Builder()
                .setQuantity(1L)
                .setPrice("price_1IWVn6GPvpv5kncL4iSLrbkX")
                .build())
                .build();

        try {
            Session session = Session.create(params);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("sessionId", session.getId());
            return new ResponseEntity<>(gson.toJson(responseData), HttpStatus.OK);
        } catch(Exception e) {
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("message", e.getMessage());
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("error", messageData);
            return new ResponseEntity<>(gson.toJson(responseData), HttpStatus.BAD_REQUEST);
        }

    }
}
