package com.fyp.companyinterfaceservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Long studentId;
    private String firstName;
    private String surname;
    private String password;
    private String email;
    private String username;
    private String phone;
    private String socialUrl;
    private Instant created;
    private Boolean enabled;
    private String role;
    private String[] authorities;
    private Boolean isLocked;
    private Long expiresIn;
    private String token;
    private StudentProfile profile;
    private Resume resume;
}
