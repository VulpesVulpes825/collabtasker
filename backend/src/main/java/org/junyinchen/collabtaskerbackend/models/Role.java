package org.junyinchen.collabtaskerbackend.models;

import jakarta.persistence.*;

import lombok.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Collection<Privilege> privileges;

    public Role(@NonNull String name) {
        this.name = name;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities =
                getPrivileges().stream()
                        .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                        .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name));
        return authorities;
    }
}
