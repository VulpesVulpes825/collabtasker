package org.junyinchen.collabtaskerbackend.models;

import jakarta.persistence.*;

import lombok.*;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TodoBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull private String title;

    @OneToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Collection<TodoItem> items;

    @OneToOne @ToString.Exclude private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Collection<User> members;
}
