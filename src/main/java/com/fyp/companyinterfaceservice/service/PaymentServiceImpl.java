package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.model.User;
import com.fyp.companyinterfaceservice.service.interfaces.PaymentService;
import com.google.gson.Gson;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final Gson gson;

    public PaymentServiceImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public ResponseEntity<Object> createCheckoutSession(User user) {
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
