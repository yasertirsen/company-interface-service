package com.fyp.companyinterfaceservice.model;

import lombok.Getter;

import static com.fyp.companyinterfaceservice.constant.Authority.ADMIN_AUTHORITIES;
import static com.fyp.companyinterfaceservice.constant.Authority.DEVELOPER_AUTHORITIES;
import static com.fyp.companyinterfaceservice.constant.Authority.MANAGER_AUTHORITIES;
import static com.fyp.companyinterfaceservice.constant.Authority.SUPER_ADMIN_AUTHORITIES;
import static com.fyp.companyinterfaceservice.constant.Authority.USER_AUTHORITIES;

@Getter
public enum Role {

    ROLE_USER(USER_AUTHORITIES),
    ROLE_DEVELOPER(DEVELOPER_AUTHORITIES),
    ROLE_MANAGER(MANAGER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private final String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }

}
