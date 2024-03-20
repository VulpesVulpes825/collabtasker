package org.junyinchen.collabtaskerbackend.services;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.User;
import org.junyinchen.collabtaskerbackend.repositories.BoardRepository;
import org.junyinchen.collabtaskerbackend.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Override
    public TodoBoard saveBoard(TodoBoard board) {
        log.info("Saving new Todo Board {} to the database", board.getTitle());
        return boardRepository.save(board);
    }

    @Override
    public void addBoardToUser(String username, long boardId) {
        log.info("Adding Todo Board {} to user {}", username, boardId);
        TodoBoard board = boardRepository.findById(boardId).orElseThrow();
        Role role =
                Role.builder()
                        .name("ROLE_BOARD_" + boardId)
                        .privileges(Collections.emptyList())
                        .build();
        roleRepository.save(role);
        userService.addRoleToUser(username, role.getName());
        board.setRole(role);
    }

    @Override
    public TodoBoard getBoard(long boardId) {
        log.info("Fetching Todo Board {}", boardId);
        return boardRepository.findById(boardId).orElseThrow();
    }

    @Override
    public User getOwner(long boardId) {
        log.info("Fetching Owner of Todo Board {}", boardId);
        TodoBoard board = boardRepository.findById(boardId).orElseThrow();
        return board.getOwner();
    }

    @Override
    public Collection<User> getMembers(long boardId) {
        TodoBoard board = boardRepository.findById(boardId).orElseThrow();
        return board.getMembers();
    }

    @Override
    public List<TodoBoard> getBoards() {
        log.info("Fetching all boards");
        return boardRepository.findAll();
    }
}
