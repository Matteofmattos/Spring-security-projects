package com.matteof.mattos.springSecurityOne.configuration.providers;

import com.matteof.mattos.springSecurityOne.configuration.MySecurityAuthentication;
import com.matteof.mattos.springSecurityOne.model.MyUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public MyAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var authenticationRequest = (MySecurityAuthentication) authentication;
        String username = authenticationRequest.getName();
        String password = authenticationRequest.getPassword();

        if ("Test".equals(username)){
            return null;
        }

        MyUser myUser = (MyUser) userDetailsService.loadUserByUsername(username);

        if (!myUser.getPassword().equals(passwordEncoder.encode(password)) ||
            !myUser.getUsername().equals(username)) {
            throw new BadCredentialsException("# Invalid credentials!");
        }

        return MySecurityAuthentication.authenticated(myUser);
    }


    //O provider precisa saber se a classe MySecurityAuthetication extende authentication.
    @Override
    public boolean supports(Class<?> authentication) {
        return MySecurityAuthentication.class.isAssignableFrom(authentication);
    }
}
