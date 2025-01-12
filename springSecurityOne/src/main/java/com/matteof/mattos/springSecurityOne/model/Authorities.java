package com.matteof.mattos.springSecurityOne.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "AUTHORITIES")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(unique = true)
    private String authority;

    public Integer getId() {
        return id;
    }

    public @NonNull String getAuthority() {
        return authority;
    }
}
