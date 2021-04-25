package com.fyp.companyinterfaceservice.controller;


import com.fyp.companyinterfaceservice.exceptions.EmailExistsException;
import com.fyp.companyinterfaceservice.exceptions.ProgradException;
import com.fyp.companyinterfaceservice.exceptions.UserNotFoundException;
import com.fyp.companyinterfaceservice.exceptions.UsernameExistsException;
import com.fyp.companyinterfaceservice.jwt.JWTTokenProvider;
import com.fyp.companyinterfaceservice.model.User;
import com.fyp.companyinterfaceservice.model.UserPrincipal;
import com.fyp.companyinterfaceservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.fyp.companyinterfaceservice.constant.SecurityConstants.EXPIRATION_TIME;

@RestController
public class UserController {

    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, JWTTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) throws UserNotFoundException, UsernameExistsException, ProgradException, EmailExistsException {
        return userService.register(user);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<User> login(@RequestBody User user) throws Exception {
        authenticate(user.getEmail(), user.getPassword());
        User loggedUser = userService.findUserByEmail(user.getEmail());
        UserPrincipal userPrincipal = new UserPrincipal(loggedUser);

        loggedUser.setExpiresIn(EXPIRATION_TIME);
        loggedUser.setToken(jwtTokenProvider.generateJwtToken(userPrincipal));

        return new ResponseEntity<>(loggedUser, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @GetMapping(value = "/verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        return userService.verifyAccount(token);
    }

    @GetMapping("/currentUser")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/sendVerify")
    public ResponseEntity<String> sendVerifyEmail(@RequestParam String email) throws ProgradException {
        return userService.sendVerifyEmail(email);
    }

    @PutMapping("/changePassword/{token}")
    public User verifyChangePassword(@PathVariable String token, @RequestParam String password) throws UserNotFoundException {
        return userService.verifyChangePassword(token, password);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/")
    public ResponseEntity<String> healthIndicator() {
        return new ResponseEntity<>("UP", HttpStatus.OK);
    }

}
