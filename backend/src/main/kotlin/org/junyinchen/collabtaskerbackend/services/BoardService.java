package org.junyinchen.collabtaskerbackend.services;

import org.junyinchen.collabtaskerbackend.models.TodoBoard;

import java.util.List;

public interface BoardService {
    TodoBoard saveBoard(TodoBoard board);

    void addBoardToUser(String username, long boardId);

    TodoBoard getBoard(long boardId);

    List<TodoBoard> getBoards();
}
