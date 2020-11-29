package com.fyp.companyinterfaceservice.controller;


import com.fyp.companyinterfaceservice.exceptions.EmailExistsException;
import com.fyp.companyinterfaceservice.exceptions.ProgradException;
import com.fyp.companyinterfaceservice.exceptions.UserNotFoundException;
import com.fyp.companyinterfaceservice.exceptions.UsernameExistsException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import static com.fyp.companyinterfaceservice.constant.SecurityConstants.EXPIRATION_TIME;

@RestController
public class CompanyController {

    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public CompanyController(UserService userService, JWTTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
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

        User loggedUser = userService.findUserByEmail(user.getEmail());
        authenticate(loggedUser.getEmail(), loggedUser.getPassword());
        UserPrincipal userPrincipal = new UserPrincipal(loggedUser);

        loggedUser.setPassword(StringUtils.EMPTY);
        loggedUser.setExpiresIn(EXPIRATION_TIME);
        loggedUser.setToken(jwtTokenProvider.generateJwtToken(userPrincipal));

        return new ResponseEntity<>(loggedUser, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @GetMapping(value = "verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        return userService.verifyAccount(token);
    }

    @PostMapping("/positions/add")
    public Position addPosition(@RequestBody Position position) {
        return userService.addPosition(position);
    }
}
