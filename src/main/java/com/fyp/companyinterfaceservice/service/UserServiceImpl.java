package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.exceptions.EmailExistsException;
import com.fyp.companyinterfaceservice.exceptions.ProgradException;
import com.fyp.companyinterfaceservice.exceptions.UserNotFoundException;
import com.fyp.companyinterfaceservice.exceptions.UsernameExistsException;
import com.fyp.companyinterfaceservice.model.NotificationEmail;
import com.fyp.companyinterfaceservice.model.User;
import com.fyp.companyinterfaceservice.model.UserPrincipal;
import com.fyp.companyinterfaceservice.model.UserProfile;
import com.fyp.companyinterfaceservice.service.interfaces.UserService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static com.fyp.companyinterfaceservice.constant.ErrorConstants.EMAIL_ALREADY_EXISTS;
import static com.fyp.companyinterfaceservice.constant.ErrorConstants.USERNAME_ALREADY_EXISTS;
import static com.fyp.companyinterfaceservice.model.Role.ROLE_USER;


@Service
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final ProgradClient progradClient;
    private final MailService mailService;
    @Value("${token.secret}")
    private String secretToken;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, ProgradClient progradClient, MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.progradClient = progradClient;
        this.mailService = mailService;
    }

    @Override
    public User register(User user) throws UsernameExistsException, EmailExistsException, UserNotFoundException, ProgradException {
        validateUsernameAndEmail(user.getName(), user.getEmail());

        String verificationToken = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        user.setIsLocked(false);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setToken(verificationToken);
        user.setProfile(new UserProfile());

        User registeredUser = progradClient.add(user);
        registeredUser.setPassword(StringUtils.EMPTY);

        mailService.sendMail(new NotificationEmail("Account Activation - Prograd Employers",
                user.getEmail(), "Thank you for signing up to Prograd Employers, " +
                "please click the link below to activate your account " +
                "http://localhost:8081/verification/" + verificationToken));

        return registeredUser;
    }

    private void validateUsernameAndEmail(String newName, String newEmail) throws UsernameExistsException, EmailExistsException {
        User userByEmail = findUserByEmail(newEmail);
        if(userByEmail != null) {
            throw new EmailExistsException(EMAIL_ALREADY_EXISTS);
        }
        User userByUsername = findUserByName(newName);
        if(userByUsername != null) {
            throw new UsernameExistsException(USERNAME_ALREADY_EXISTS);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return progradClient.findByEmail(secretToken, email).getBody();
    }

    @Override
    public User findUserByName(String name) {
        return progradClient.findByName(secretToken, name).getBody();
    }

    @Override
    public User findUserByToken(String token) {
        return progradClient.findByToken(secretToken, token).getBody();
    }

    @Override
    public ResponseEntity<String> verifyAccount(String token) {
        User user = findUserByToken(token);
        user.setEnabled(true);
        progradClient.update(secretToken, user);

        return new ResponseEntity<>(new Gson().toJson("Account Activated Successfully"), HttpStatus.OK);
    }

    @Override
    public User updateUser(User user) {
        return progradClient.update(secretToken, user);
    }

    @Override
    public ResponseEntity<String> sendVerifyEmail(String email) throws ProgradException {
        User user = findUserByEmail(email);
        String token = UUID.randomUUID().toString();
        mailService.sendMail(new NotificationEmail("Change Password - Prograd Employers",
                user.getEmail(), "We received a change password request, " +
                "please click the link below to change your password " +
                "http://localhost:4201/new-password/" + token));
        user.setToken(token);
        updateUser(user);
        return new ResponseEntity<>(new Gson().toJson("Verification email sent"), HttpStatus.OK);
    }

    @Override
    public User verifyChangePassword(String token, String password) {
        User user = findUserByToken(token);
        user.setPassword(passwordEncoder.encode(password));
        return updateUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByEmail(username);
        return new UserPrincipal(user);
    }

    @Override
    public User getCurrentUser() {
        String email = (String) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return findUserByEmail(email);
    }
}
