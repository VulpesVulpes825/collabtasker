package org.junyinchen.collabtaskerbackend.services;

import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.User;

import java.util.Collection;
import java.util.List;

/**
 * Interface for board service operations, offering methods to manage and interact with boards. It
 * provides functionality to save boards, associate boards with users, retrieve boards and their
 * details, including owners and members, as well as retrieving a list of all boards.
 */
public interface BoardService {
    /**
     * Saves a board to the database.
     *
     * @param board the board to save
     * @return the saved board
     */
    TodoBoard saveBoard(TodoBoard board);

    /**
     * Associates a board with a user by adding the board to the user's list of boards.
     *
     * @param username the username of the user
     * @param boardId the ID of the board to add
     */
    void addBoardToUser(String username, long boardId);

    /**
     * Retrieves a board by its ID.
     *
     * @param boardId the ID of the board to retrieve
     * @return the board
     */
    TodoBoard getBoard(long boardId);

    /**
     * Retrieves the owner of a board by the board's ID.
     *
     * @param boardId the ID of the board
     * @return the user who owns the board
     */
    User getOwner(long boardId);

    /**
     * Retrieves all members associated with a board by the board's ID.
     *
     * @param boardId the ID of the board
     * @return a collection of users who are members of the board
     */
    Collection<User> getMembers(long boardId);

    /**
     * Retrieves a list of all boards.
     *
     * @return a list of all boards
     */
    List<TodoBoard> getBoards();
}
