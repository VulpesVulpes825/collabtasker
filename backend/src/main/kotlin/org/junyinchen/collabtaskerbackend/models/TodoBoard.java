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
    private Collection<TodoItem> items;

    @OneToOne private Role role;
}
