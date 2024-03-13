package org.junyinchen.collabtaskerbackend.models;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;
import lombok.extern.java.Log;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Log
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TodoItem {
  /** The id of the item in UUID */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /** The tile of the item */
  @NonNull private String title = "";

  /** The context user put in the item */
  @NonNull private String content = "";

  /** Auto generated created time */
  @CreationTimestamp private Instant createdOn;

  /** Auto generated updated time */
  @UpdateTimestamp private Instant lastUpdatedOn;

  /** The user set due date of the item, optional */
  @Basic
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date utilTimestamp;
}
