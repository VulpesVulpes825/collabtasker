package org.junyinchen.collabtaskerbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junyinchen.collabtaskerbackend.loader.SetupDataLoader;
import org.junyinchen.collabtaskerbackend.models.TodoItem;
import org.junyinchen.collabtaskerbackend.repositories.ItemRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemServiceTest {
    @MockBean SetupDataLoader setupDataLoader;
    @Mock ItemRepository itemRepository;
    @InjectMocks ItemServiceImpl itemService;
    TodoItem item;

    @BeforeEach
    public void setup() {
        item = TodoItem.builder().title("test").build();
    }

    @Test
    void testSaveItem() {
        when(itemRepository.save(any(TodoItem.class))).thenReturn(item);
        TodoItem savedItem = itemService.saveItem(item);
        assertEquals(item.getTitle(), savedItem.getTitle());
        verify(itemRepository).save(any(TodoItem.class));
    }
}
