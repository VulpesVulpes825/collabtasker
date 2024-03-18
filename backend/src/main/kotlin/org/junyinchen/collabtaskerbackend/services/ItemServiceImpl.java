package org.junyinchen.collabtaskerbackend.services;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.TodoItem;
import org.junyinchen.collabtaskerbackend.repositories.BoardRepository;
import org.junyinchen.collabtaskerbackend.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemServie {

    private final BoardRepository boardRepository;
    private final ItemRepository itemRepository;

    @Override
    public TodoItem saveItem(TodoItem item) {
        log.info("Saving new Todo Item {} to the database", item.getTitle());
        return itemRepository.save(item);
    }

    @Override
    public void addItemToBoard(UUID itemId, long boardId) {
        log.info("Adding Todo Item {} to board {}", itemId, boardId);
        TodoBoard board = boardRepository.findById(boardId).orElseThrow();
        TodoItem item = itemRepository.findById(itemId).orElseThrow();
        board.getItems().add(item);
        item.setBoard(board);
    }

    @Override
    public TodoItem getItem(UUID id) {
        return itemRepository.findById(id).orElseThrow();
    }
}
