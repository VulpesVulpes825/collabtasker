package org.junyinchen.collabtaskerbackend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    private UUID id;
    private String title;
    private String content;
    private Date until;
    private boolean isComplete;
}
