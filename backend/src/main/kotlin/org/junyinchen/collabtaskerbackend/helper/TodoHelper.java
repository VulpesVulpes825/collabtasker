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

@Component
@RequiredArgsConstructor
@Slf4j
public class TodoHelper {

    private final UserService userService;
    private final BoardService boardService;

    public TodoBoard checkRole(long id, String username) {
        log.info("User {} is trying to access Todo Board {}", username, id);
        User user = userService.getUser(username);
        TodoBoard board = boardService.getBoard(id);
        if (!user.getRoles().contains(board.getRole())) {
            log.info(
                    "User {} does not have access to Todo Board {}",
                    username,
                    board.getId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        log.info("User {} has access to Todo Board {}", username, board.getId());
        return board;
    }

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
