package com.fyp.companyinterfaceservice.service.interfaces;

import com.fyp.companyinterfaceservice.exceptions.EmailExistsException;
import com.fyp.companyinterfaceservice.exceptions.ProgradException;
import com.fyp.companyinterfaceservice.exceptions.UserNotFoundException;
import com.fyp.companyinterfaceservice.exceptions.UsernameExistsException;
import com.fyp.companyinterfaceservice.model.Position;
import com.fyp.companyinterfaceservice.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface UserService {

    User register(User user) throws UsernameExistsException, EmailExistsException, UserNotFoundException, ProgradException;

    User findUserByEmail(String email);

    User findUserByName(String name);

    User findUserByToken(String Token);

    User getCurrentUser();

    ResponseEntity<String> verifyAccount(String token);

    Position addPosition(Position position);

    User updateUser(User user);

    Set<Position> getCompanyPositions(Long id);
}
