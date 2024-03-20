package org.junyinchen.collabtaskerbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junyinchen.collabtaskerbackend.loader.SetupDataLoader;
import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.repositories.BoardRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoardServiceTest {
    @MockBean SetupDataLoader setupDataLoader;
    @Mock BoardRepository boardRepository;
    @InjectMocks BoardServiceImpl boardService;
    TodoBoard board;

    @BeforeEach
    public void setup() {
        board = TodoBoard.builder().title("Test Board").build();
    }

    @Test
    void testSaveBoard() {
        when(boardRepository.save(any(TodoBoard.class))).thenReturn(board);
        TodoBoard savedBoard = boardService.saveBoard(board);
        assertEquals(board.getTitle(), savedBoard.getTitle());
        verify(boardRepository).save(any(TodoBoard.class));
    }
}
