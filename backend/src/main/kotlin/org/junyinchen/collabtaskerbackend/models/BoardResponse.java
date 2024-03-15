package org.junyinchen.collabtaskerbackend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    private String title;
    private Collection<ItemResponse> items;
}
