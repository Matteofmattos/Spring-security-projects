package com.matteof.mattos.springSecurityOne.configuration;

import com.matteof.mattos.springSecurityOne.model.MyUser;
import com.matteof.mattos.springSecurityOne.model.Users;
import com.matteof.mattos.springSecurityOne.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> userByUsername = usersRepository.findByUsername(username);
        if (userByUsername.isEmpty()) {
            throw new UsernameNotFoundException("Invalid credentials!");
        }

        Users user = userByUsername.get();
        if (user == null || !user.getUsername().equals(username)) {
            throw new UsernameNotFoundException("Invalid credentials!");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getAuthorities().forEach(authority ->
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority())));

        return new MyUser(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities,
                user.getFirstName(), user.getLastName(), user.getEmailAddress(), user.getBirthdate());
    }
}
