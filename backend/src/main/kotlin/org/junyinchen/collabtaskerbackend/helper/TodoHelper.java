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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TodoHelper {

    private final UserService userService;
    private final BoardService boardService;

    public TodoBoard checkRole(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} is trying to access Todo Board {}", authentication.getName(), id);
        User user = userService.getUser(authentication.getName());
        TodoBoard board = boardService.getBoard(id);
        if (!user.getRoles().contains(board.getRole())) {
            log.info(
                    "User {} does not have access to Todo Board {}",
                    authentication.getName(),
                    board.getId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        log.info("User {} has access to Todo Board {}", authentication.getName(), board.getId());
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
