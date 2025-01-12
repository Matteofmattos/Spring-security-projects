package com.matteof.mattos.springSecurityOne.configuration.providers;

import com.matteof.mattos.springSecurityOne.configuration.MySecurityAuthentication;
import com.matteof.mattos.springSecurityOne.model.MyUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class TestAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.err.println("TestProvider ");
        var authRequest = (MySecurityAuthentication) authentication;
        var password = authRequest.getPassword();
        var username = authRequest.getName();
        if (!"Test".equals(username)) {
            return null;
        }
        MyUser user = new MyUser(username, password, true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN", "ROLE_DEVELOPER"), "", "", "", null);
        return MySecurityAuthentication.authenticated(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MySecurityAuthentication.class.isAssignableFrom(authentication);
    }
}
