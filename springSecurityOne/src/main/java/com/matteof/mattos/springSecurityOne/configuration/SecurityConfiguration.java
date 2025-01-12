package com.matteof.mattos.springSecurityOne.configuration;

import com.matteof.mattos.springSecurityOne.configuration.providers.MyAuthenticationProvider;
import com.matteof.mattos.springSecurityOne.configuration.providers.TestAuthenticationProvider;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEventPublisher publisher, UserDetailsService userDetailsService) throws Exception {

        List<AuthenticationProvider> authProviders = new ArrayList<>();

        authProviders.add(new MyAuthenticationProvider(userDetailsService));
        authProviders.add(new TestAuthenticationProvider());

        ProviderManager providerManager = new ProviderManager(authProviders);

        providerManager.setAuthenticationEventPublisher(publisher);

        http
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.GET, "/home").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("ROLE_USER");
                    authConfig.requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN");
                    authConfig.anyRequest().authenticated();
                })

                .addFilterBefore(new MySecurityFilter(providerManager),UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                //.userDetailsService(new MyUserDetailsService())
                .formLogin(withDefaults()) // Login with browser and Build in Form
                .httpBasic(withDefaults()); // Login with Insomnia or Postman and Basic Auth
        return http.build();
    }

    @Bean
    UserDetailsService myUserDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> successEvent() {
        return event -> {
            System.err.println("Success Login " + event.getAuthentication().getClass().getName() + " - " + event.getAuthentication().getName());
        };
    }

    @Bean
    public ApplicationListener<AuthenticationFailureBadCredentialsEvent> failureEvent() {
        return event -> {
            System.err.println("Bad Credentials Login " + event.getAuthentication().getClass().getName() + " - " + event.getAuthentication().getName());
        };
    }
}
