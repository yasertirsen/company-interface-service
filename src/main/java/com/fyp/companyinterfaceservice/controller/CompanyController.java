package com.fyp.companyinterfaceservice.controller;


import com.fyp.companyinterfaceservice.exceptions.*;
import com.fyp.companyinterfaceservice.jwt.JWTTokenProvider;
import com.fyp.companyinterfaceservice.model.Position;
import com.fyp.companyinterfaceservice.model.User;
import com.fyp.companyinterfaceservice.model.UserPrincipal;
import com.fyp.companyinterfaceservice.service.interfaces.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Set;

import static com.fyp.companyinterfaceservice.constant.SecurityConstants.EXPIRATION_TIME;

@RestController
public class CompanyController {

    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public CompanyController(UserService userService, JWTTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) throws UserNotFoundException, UsernameExistsException, ProgradException, EmailExistsException {
        return userService.register(user);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<User> login(@RequestBody User user) throws Exception {

        User loggedUser = userService.findUserByEmail(user.getEmail());
        if(passwordEncoder.matches(user.getPassword(), loggedUser.getPassword())) {
            authenticate(loggedUser.getEmail(), loggedUser.getPassword());
            UserPrincipal userPrincipal = new UserPrincipal(loggedUser);

            loggedUser.setExpiresIn(EXPIRATION_TIME);
            loggedUser.setToken(jwtTokenProvider.generateJwtToken(userPrincipal));

            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        }
        else
            throw new IncorrectPasswordException();
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

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

//    @PostMapping("/changePassword")
//    public User changePassword(@RequestParam String email, @RequestParam String password) {
//        User user = this.userService.findUserByEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        return this.userService.updateUser(user);
//    }

}
