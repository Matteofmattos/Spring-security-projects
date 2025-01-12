package com.matteof.mattos.springSecurityOne.controller;

import com.matteof.mattos.springSecurityOne.model.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {


    @GetMapping("/home")
    public String home() {
        return "<h1>Welcome home!</h1>";
    }

    @GetMapping("/user")
    public String user(Authentication authentication) {
        return "<h1>Welcome User!</h1><h2>" + authentication.getName() + "</h2>";
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication) {
        MyUser myUser = (MyUser) authentication.getPrincipal();
        return "<h1>Welcome Admin!</h1><h2>" + authentication.getName() +
                "</h2><p>MyUser:<br>User name: " + myUser.getUsername() +"<br>"
                + "Full name: " + myUser.getFirstName()+ " " + myUser.getLastName() + "<br>"
                + "E-Mail: " + myUser.getEmailAddress() + "<br> Birthday: " + myUser.getBirthdate()
                + "Authorities: " + myUser.getAuthorities();
    }
}
