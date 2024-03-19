package org.junyinchen.collabtaskerbackend.services;

import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.User;

import java.util.Collection;
import java.util.List;

public interface BoardService {
    TodoBoard saveBoard(TodoBoard board);

    void addBoardToUser(String username, long boardId);

    TodoBoard getBoard(long boardId);

    User getOwner(long boardId);

    Collection<User> getMembers(long boardId);

    List<TodoBoard> getBoards();
}
