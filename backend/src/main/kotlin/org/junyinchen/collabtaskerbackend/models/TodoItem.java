package org.junyinchen.collabtaskerbackend.models;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TodoItem {
    /** The id of the item in UUID */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** The tile of the item */
    @NonNull private String title;

    /** The context user put in the item */
    private String content;

    /** Auto generated created time */
    @CreationTimestamp private Instant createdOn;

    /** Auto generated updated time */
    @UpdateTimestamp private Instant lastUpdatedOn;

    /** The user set due date of the item, optional */
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date utilTimestamp;

    private boolean isComplete = false;

    @ManyToOne(fetch = FetchType.EAGER)
    private TodoBoard board;
}
