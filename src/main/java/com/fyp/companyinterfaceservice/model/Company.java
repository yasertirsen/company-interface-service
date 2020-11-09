package com.fyp.companyinterfaceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String email;
    private String password;
    private String name;
    private String companyUrl;
    private String address;
    private String recruiter;
    private String recruiterPhone;
    private boolean enabled;
    private Instant created;
}
