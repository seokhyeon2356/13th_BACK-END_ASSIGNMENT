package com.acycycy.LikeLionWeek2.service;

import com.acycycy.LikeLionWeek2.dto.TodoDto;
import com.acycycy.LikeLionWeek2.entity.TodoEntity;
import com.acycycy.LikeLionWeek2.entity.UserEntity;
import com.acycycy.LikeLionWeek2.repository.TodoRepository;
import com.acycycy.LikeLionWeek2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public List<TodoDto> getTodosByUsername(String username) {
        return todoRepository.findTodosByUsername(username).stream()
                .map(TodoDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<TodoDto> getTodosByUsernameAndCompletion(String username, boolean completed) {
        return todoRepository.findTodosByUsernameAndCompleted(username, completed).stream()
                .map(TodoDto::fromEntity)
                .collect(Collectors.toList());
    }

    public TodoDto createTodo(TodoDto dto) {
        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        TodoEntity todo = TodoEntity.builder()
                .content(dto.getContent())
                .completed(false)
                .user(user)
                .build();
        return TodoDto.fromEntity(todoRepository.save(todo));
    }

    public TodoDto completeTodo(Long id) {
        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID[" + id + "] 투두를 찾을 수 없습니다."));
        todo.setCompleted(true);
        return TodoDto.fromEntity(todoRepository.save(todo));
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
