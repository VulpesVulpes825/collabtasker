package org.junyinchen.collabtaskerbackend.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.models.*;
import org.junyinchen.collabtaskerbackend.services.ItemServie;
import org.junyinchen.collabtaskerbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Slf4j
public class TodoItemController {
    private final UserService userService;
    private final ItemServie itemServie;

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getById(@PathVariable String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("User {} is trying to access Todo Item {}", username, id);
        User user = userService.getUser(username);
        TodoItem item = itemServie.getItem(UUID.fromString(id));
        if (!user.getRoles().contains(item.getBoard().getRole())) {
            log.info("User {} does not have access to Todo Item {}", username, id);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        log.info("User {} has access to Todo Item {}", username, id);
        return ResponseEntity.ok(responseBuilder(item));
    }

    @PostMapping("/")
    public ResponseEntity<ItemResponse> create(@RequestBody ItemRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID itemId = request.getId();
        log.info("User {} is trying to update Todo Item {}", username, itemId);
        User user = userService.getUser(username);
        TodoItem item = itemServie.getItem(itemId);
        TodoBoard board = item.getBoard();
        if (!user.getRoles().contains(board.getRole())) {
            log.info("User {} does not have access to Todo Board {}", username, board.getId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        log.info("User {} has access to update Todo Item {}, updating", username, itemId);
        item.setComplete(request.isComplete());
        item.setTitle(request.getTitle());
        item.setContent(request.getContent());
        if (request.getUntil() != null) {
            item.setUntil(request.getUntil());
        }
        item.setComplete(request.isComplete());
        itemServie.saveItem(item);
        return ResponseEntity.ok(responseBuilder(item));
    }

    private ItemResponse responseBuilder(TodoItem item) {
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
