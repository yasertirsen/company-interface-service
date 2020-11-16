package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.exceptions.EmailExistsException;
import com.fyp.companyinterfaceservice.exceptions.ProgradException;
import com.fyp.companyinterfaceservice.exceptions.UserNotFoundException;
import com.fyp.companyinterfaceservice.exceptions.UsernameExistsException;
import com.fyp.companyinterfaceservice.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User register(User user) throws UsernameExistsException, EmailExistsException, UserNotFoundException, ProgradException;

    User findUserByEmail(String email);

    User findUserByName(String name);

    User findUserByToken(String Token);

    User login(User user) throws Exception;

    ResponseEntity<String> verifyAccount(String token);
}
