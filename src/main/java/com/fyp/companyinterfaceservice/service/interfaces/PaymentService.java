package com.fyp.companyinterfaceservice.service.interfaces;

import com.fyp.companyinterfaceservice.model.User;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<Object> createCheckoutSession(User user);
}
