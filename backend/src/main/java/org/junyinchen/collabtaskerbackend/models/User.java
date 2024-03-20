package org.junyinchen.collabtaskerbackend.models;

import jakarta.persistence.*;

import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Column(unique = true)
    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private boolean enabled;

    private boolean tokenExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Collection<Role> roles;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @ToString.Exclude
    private Collection<TodoBoard> ownedBoard;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "members")
    private Collection<TodoBoard> boards;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(a -> a.getAuthorities().stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
