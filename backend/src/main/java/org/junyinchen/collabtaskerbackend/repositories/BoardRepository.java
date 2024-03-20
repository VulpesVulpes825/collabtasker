package org.junyinchen.collabtaskerbackend.repositories;

import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<TodoBoard, Long> {}
