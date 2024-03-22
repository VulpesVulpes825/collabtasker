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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Controller for managing boards, including CRUD operations for boards and their items. It uses
 * {@link BoardService} and {@link UserService} for handling board and user-related operations,
 * respectively, and {@link TodoHelper} for auxiliary functionality like role checking and response
 * building.
 */
@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@Slf4j
public class TodoBoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final TodoHelper todoHelper;
    private final ItemServie itemServie;

    /**
     * Retrieves a board by its id.
     *
     * @param id the id of the board to retrieve
     * @return a {@link ResponseEntity} containing the board response with status OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getById(@PathVariable String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                responseBuilder(todoHelper.checkRole(Long.parseLong(id), username)));
    }

    /**
     * Retrieves all boards associated with the currently authenticated user.
     *
     * @return a {@link ResponseEntity} containing a list of boards responses with status OK
     */
    @GetMapping("/")
    public ResponseEntity<List<BoardsResponse>> getAllBoard() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("User {} is trying get all boards", username);
        Collection<TodoBoard> boards = userService.getAllBoards(username);
        return ResponseEntity.ok(
                boards.stream()
                        .map(
                                a ->
                                        BoardsResponse.builder()
                                                .id(a.getId())
                                                .title(a.getTitle())
                                                .build())
                        .toList());
    }

    /**
     * Creates a new board with the provided details.
     *
     * @param request the request containing the details for the new board
     * @return a {@link ResponseEntity} containing the board response with status OK
     */
    @PostMapping("/")
    public ResponseEntity<BoardResponse> create(@RequestBody BoardRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(
                "User {} is trying to create a new Todo Board named {}",
                username,
                request.getTitle());
        User user = userService.getUser(username).orElseThrow();
        TodoBoard board = TodoBoard.builder().title(request.getTitle()).owner(user).build();
        boardService.saveBoard(board);
        boardService.addBoardToUser(username, board.getId());
        return ResponseEntity.ok(
                BoardResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .items(Collections.emptyList())
                        .build());
    }

    /**
     * Creates a new item in the specified board.
     *
     * @param request the request containing the details for the new item
     * @param id the id of the board where the item will be added
     * @return a {@link ResponseEntity} containing the item response with status OK
     */
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

    /**
     * Builds a {@link BoardResponse} from a {@link TodoBoard}.
     *
     * @param board the board to build the response from
     * @return the board response
     */
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
