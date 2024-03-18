package org.junyinchen.collabtaskerbackend.services;

import org.junyinchen.collabtaskerbackend.models.TodoItem;

import java.util.UUID;

public interface ItemServie {
    TodoItem saveItem(TodoItem item);

    void addItemToBoard(UUID id, long boardId);

    TodoItem getItem(UUID id);
}
