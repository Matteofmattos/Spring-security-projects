package com.matteof.mattos.springSecurityOne.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    private String password;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private Date birthdate;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities", joinColumns = {
            @JoinColumn(name = "USERS_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
            @JoinColumn(name = "AUTHORITIES_ID", referencedColumnName = "ID") })
    private Set<Authorities> authorities;

    @Builder.Default
    private Boolean accountNonExpired = true;
    @Builder.Default
    private Boolean accountNonLocked = true;
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    @Builder.Default
    private Boolean enabled = true;

}