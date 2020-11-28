package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.exceptions.EmailExistsException;
import com.fyp.companyinterfaceservice.exceptions.ProgradException;
import com.fyp.companyinterfaceservice.exceptions.UserNotFoundException;
import com.fyp.companyinterfaceservice.exceptions.UsernameExistsException;
import com.fyp.companyinterfaceservice.model.NotificationEmail;
import com.fyp.companyinterfaceservice.model.Position;
import com.fyp.companyinterfaceservice.model.User;
import com.fyp.companyinterfaceservice.model.UserPrincipal;
import com.fyp.companyinterfaceservice.model.UserProfile;
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
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final ProgradClient progradClient;
    private final MailService mailService;

  @Value("${token.secret}")
  private String bearerToken;

    @Autowired
    public UserServiceImplementation(BCryptPasswordEncoder passwordEncoder, ProgradClient progradClient, MailService mailService) {
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
        return progradClient.findByEmail(bearerToken, email).getBody();
    }

    @Override
    public User findUserByName(String name) {
        return progradClient.findByName(bearerToken, name).getBody();
    }

    @Override
    public User findUserByToken(String token) {
        return progradClient.findByToken(bearerToken, token).getBody();
    }

    @Override
    public ResponseEntity<String> verifyAccount(String token) {
        User user = findUserByToken(token);
        user.setEnabled(true);
        progradClient.update(bearerToken, user);

        return new ResponseEntity<>(new Gson().toJson("Account Activated Successfully"), HttpStatus.OK);
    }

    @Override
    public Position addPosition(Position position) {
        return progradClient.addPosition(position);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByEmail(username);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return new UserPrincipal(user);
    }

    @Override
    public User getCurrentUser() {
        User principal = (User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return findUserByEmail(principal.getEmail());
    }
}
