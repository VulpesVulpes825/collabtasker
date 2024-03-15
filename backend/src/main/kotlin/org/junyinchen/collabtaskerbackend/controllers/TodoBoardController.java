package org.junyinchen.collabtaskerbackend.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.models.BoardResponse;
import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.User;
import org.junyinchen.collabtaskerbackend.services.BoardService;
import org.junyinchen.collabtaskerbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@Slf4j
public class TodoBoardController {
    private final BoardService boardService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getById(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} is trying to access Todo Board {}", authentication.getName(), id);
        User user = userService.getUser(authentication.getName());
        TodoBoard board = boardService.getBoard(Long.parseLong(id));
        if (user.getRoles().contains(board.getRole())) {
            log.info(
                    "User {} has access to Todo Board {}", authentication.getName(), board.getId());
        }
        return null;
    }
}
