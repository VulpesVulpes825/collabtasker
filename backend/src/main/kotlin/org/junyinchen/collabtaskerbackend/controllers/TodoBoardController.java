package org.junyinchen.collabtaskerbackend.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.helper.TodoHelper;
import org.junyinchen.collabtaskerbackend.models.*;
import org.junyinchen.collabtaskerbackend.services.BoardService;
import org.junyinchen.collabtaskerbackend.services.ItemServie;
import org.junyinchen.collabtaskerbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@Slf4j
public class TodoBoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final TodoHelper todoHelper;
    private final ItemServie itemServie;

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getById(@PathVariable String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                responseBuilder(todoHelper.checkRole(Long.parseLong(id), username)));
    }

    @PostMapping("/")
    public ResponseEntity<BoardResponse> create(@RequestBody BoardRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(
                "User {} is trying to create a new Todo Board named {}",
                username,
                request.getTitle());
        TodoBoard board = TodoBoard.builder().title(request.getTitle()).build();
        boardService.saveBoard(board);
        boardService.addBoardToUser(username, board.getId());
        return ResponseEntity.ok(
                BoardResponse.builder()
                        .title(board.getTitle())
                        .items(Collections.emptyList())
                        .build());
    }

    @PostMapping("/{id}/item")
    public ResponseEntity<ItemResponse> createItem(
            @RequestBody ItemRequest request, @PathVariable String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TodoBoard board = todoHelper.checkRole(Long.parseLong(id), username);
        TodoItem item =
                TodoItem.builder()
                        .title(request.getTitle())
                        .board(board)
                        .owner(userService.getUser(username).orElseThrow())
                        .build();
        itemServie.saveItem(item);
        itemServie.addItemToBoard(item.getId(), board.getId());
        return ResponseEntity.ok(todoHelper.responseBuilder(item));
    }

    private BoardResponse responseBuilder(TodoBoard board) {
        List<ItemResponse> items =
                board.getItems().stream()
                        .map(a -> ItemResponse.builder().id(a.getId()).title(a.getTitle()).build())
                        .toList();
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .items(items)
                .build();
    }
}
