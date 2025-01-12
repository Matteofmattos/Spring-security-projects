package com.matteof.mattos.springSecurityOne.configuration;

import com.matteof.mattos.springSecurityOne.model.MyUser;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

public class MySecurityAuthentication implements Authentication {

    @Serial
    private static final long serialVersionUID = 1L;

    private final boolean isAuthenticated;

    private final String name;

    @Getter
    private final String password;

    private final MyUser myUser;

    private final Collection<GrantedAuthority> authorities;

    private MySecurityAuthentication(Collection<GrantedAuthority> authorities, String name, MyUser myUser, String password) {
        this.authorities = authorities;
        this.name = name;
        this.password = password;
        this.myUser = myUser;
        this.isAuthenticated = (password == null);
    }

    // Used by Security Filter
    public static MySecurityAuthentication unauthenticated(String name, String password) {
        return new MySecurityAuthentication(Collections.emptyList(), name, null, password);
    }

    // Used by Security Authentication Provider after loadUserByUsername returns the correct user.
    public static MySecurityAuthentication authenticated(MyUser myUser) {
        return new MySecurityAuthentication(myUser.getAuthorities(), myUser.getUsername(), myUser, null);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.myUser;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Don't do this.");
    }

    @Override
    public String getName() {
        return this.name;
    }
}
