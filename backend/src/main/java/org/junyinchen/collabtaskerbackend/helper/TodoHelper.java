package org.junyinchen.collabtaskerbackend.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.models.ItemResponse;
import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.TodoItem;
import org.junyinchen.collabtaskerbackend.models.User;
import org.junyinchen.collabtaskerbackend.services.BoardService;
import org.junyinchen.collabtaskerbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

/**
 * A helper class for the application that provides common functionalities such as access control
 * checks and response building. It uses {@link UserService} to retrieve user details and {@link
 * BoardService} to retrieve board details.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TodoHelper {

    private final UserService userService;
    private final BoardService boardService;

    /**
     * Verifies if the user has the appropriate role to access a specified TodoBoard. It checks the
     * user's roles against the required role of the TodoBoard. If the user does not have the
     * required role, it throws a {@link ResponseStatusException} with HTTP status FORBIDDEN.
     *
     * @param id the identifier of the TodoBoard
     * @param username the username of the user attempting to access the board
     * @return the TodoBoard if the user has access
     * @throws ResponseStatusException with HttpStatus.FORBIDDEN if the user does not have access
     */
    public TodoBoard checkRole(long id, String username) {
        log.info("User {} is trying to access Todo Board {}", username, id);
        User user = userService.getUser(username).orElseThrow();
        TodoBoard board = boardService.getBoard(id);
        if (!user.getRoles().contains(board.getRole())) {
            log.info("User {} does not have access to Todo Board {}", username, board.getId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        log.info("User {} has access to Todo Board {}", username, board.getId());
        return board;
    }

    /**
     * Builds an {@link ItemResponse} from a {@link TodoItem}. This method constructs a response
     * including item details such as id, title, content, completion status, creation date, last
     * updated date, and due date.
     *
     * @param item the TodoItem to build the response from
     * @return an ItemResponse containing the details of the TodoItem
     */
    public ItemResponse responseBuilder(TodoItem item) {
        return ItemResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .content(item.getContent())
                .isComplete(item.isComplete())
                .createdOn(Date.from(item.getCreatedOn()))
                .lastUpdatedOn(Date.from(item.getLastUpdatedOn()))
                .util(item.getUntil())
                .build();
    }
}
