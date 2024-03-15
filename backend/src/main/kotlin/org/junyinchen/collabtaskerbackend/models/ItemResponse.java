package org.junyinchen.collabtaskerbackend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private long id;
    private String title;
    private String content;
    private Date createdOn;
    private Date lastUpdatedOn;
    private Date util;
    private boolean isComplete;
}
