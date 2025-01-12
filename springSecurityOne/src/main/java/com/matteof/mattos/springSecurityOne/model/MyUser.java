package com.matteof.mattos.springSecurityOne.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;
import java.util.Date;

@Setter
@Getter
public class MyUser extends User {

    @Serial
    private static final long serialVersionUID = 1L;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private Date birthdate;

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
                  String firstName, String lastName, String emailaddress, Date birthdate) {
        super(username, password, enabled, accountNonExpired,credentialsNonExpired,accountNonLocked, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailaddress;
        this.birthdate = birthdate;
    }
}
