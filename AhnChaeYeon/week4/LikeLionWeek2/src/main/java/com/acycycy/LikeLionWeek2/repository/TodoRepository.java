package com.acycycy.LikeLionWeek2.repository;

import com.acycycy.LikeLionWeek2.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    @Query(value = "SELECT t.* FROM todos t JOIN users u ON t.user_id = u.id WHERE u.username = :username", nativeQuery = true)
    List<TodoEntity> findTodosByUsername(String username);

    @Query(value = "SELECT t.* FROM todos t JOIN users u ON t.user_id = u.id WHERE u.username = :username AND t.completed = :completed", nativeQuery = true)
    List<TodoEntity> findTodosByUsernameAndCompleted(String username, boolean completed);
}