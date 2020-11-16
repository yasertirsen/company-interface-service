package com.fyp.companyinterfaceservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long companyId;
    private String email;
    private String password;
    private String name;
    private String companyUrl;
    private String address;
    private String recruiter;
    private String recruiterPhone;
    private Instant created;
    private String role;
    private String[] authorities;
    private Boolean isLocked;
    private Long expiresIn;
    private String token;
    private Boolean enabled;
    private UserProfile profile;
}
