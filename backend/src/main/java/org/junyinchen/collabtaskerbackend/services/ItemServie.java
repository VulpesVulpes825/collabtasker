package org.junyinchen.collabtaskerbackend.services;

import org.junyinchen.collabtaskerbackend.models.TodoItem;

import java.util.Optional;
import java.util.UUID;

public interface ItemServie {
    TodoItem saveItem(TodoItem item);

    void addItemToBoard(UUID id, long boardId);

    Optional<TodoItem> getItem(UUID id);

    void deleteItem(TodoItem item);
}
