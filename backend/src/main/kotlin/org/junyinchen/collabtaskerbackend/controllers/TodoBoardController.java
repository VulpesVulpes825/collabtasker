package org.junyinchen.collabtaskerbackend.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.models.*;
import org.junyinchen.collabtaskerbackend.services.BoardService;
import org.junyinchen.collabtaskerbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

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
        if (!user.getRoles().contains(board.getRole())) {
            log.info(
                    "User {} does not have access to Todo Board {}",
                    authentication.getName(),
                    board.getId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        log.info("User {} has access to Todo Board {}", authentication.getName(), board.getId());
        List<ItemResponse> items =
                board.getItems().stream()
                        .map(
                                a ->
                                        ItemResponse.builder()
                                                .id(a.getId())
                                                .title(a.getTitle())
                                                .content(a.getContent())
                                                .isComplete(a.isComplete())
                                                .createdOn(Date.from(a.getCreatedOn()))
                                                .lastUpdatedOn(Date.from(a.getLastUpdatedOn()))
                                                .util(a.getUtilTimestamp())
                                                .build())
                        .toList();
        return ResponseEntity.ok(
                BoardResponse.builder().title(board.getTitle()).items(items).build());
    }
}
