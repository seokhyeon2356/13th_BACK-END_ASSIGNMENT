package com.acycycy.LikeLionWeek2.service;

import com.acycycy.LikeLionWeek2.dto.TodoDto;
import com.acycycy.LikeLionWeek2.entity.TodoEntity;
import com.acycycy.LikeLionWeek2.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoEntity> findAll() {
        return todoRepository.findAll();
    }

    public TodoEntity create(TodoDto dto) {
        return todoRepository.save(TodoEntity.builder()
                .content(dto.getContent())
                .completed(false)
                .build());
    }

    public TodoEntity update(Long id, TodoDto dto) {
        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("할 일을 찾을 수 없습니다."));
        todo = TodoEntity.builder()
                .id(id)
                .content(dto.getContent())
                .completed(dto.isCompleted())
                .build();
        return todoRepository.save(todo);
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    public TodoEntity markComplete(Long id) {
        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow();
        todo = TodoEntity.builder()
                .id(id)
                .content(todo.getContent())
                .completed(true)
                .build();
        return todoRepository.save(todo);
    }

    public TodoEntity revert(Long id) {
        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow();
        todo = TodoEntity.builder()
                .id(id)
                .content(todo.getContent())
                .completed(false)
                .build();
        return todoRepository.save(todo);
    }
}
