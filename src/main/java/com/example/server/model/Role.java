package com.example.server.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Noemi on 09.04.2022
 */
public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }
}
