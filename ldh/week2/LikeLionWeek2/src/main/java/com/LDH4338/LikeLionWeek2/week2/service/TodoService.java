package com.LDH4338.LikeLionWeek2.week2.service;

import com.LDH4338.LikeLionWeek2.week2.dto.TodoDto;
import com.LDH4338.LikeLionWeek2.week2.entity.Todo;
import com.LDH4338.LikeLionWeek2.week2.repository.TodoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    //할 일 추가
    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = todoRepository.save(todoDto.toEntity());
        return new TodoDto(todo);
    }

    //전체 할 일 조회
    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(TodoDto::new)
                .collect(Collectors.toList());
    }

    //할 일 수정
    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 할 일을 찾을 수 없습니다."));

        todo.setContent(todoDto.getContent());
        todo.setCompleted(todoDto.isCompleted());

        return new TodoDto(todoRepository.save(todo));
    }

    //할 일 삭제
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    //상태 변경 할 일 (미완 <-> 완료)
    public TodoDto changedCompleted(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 할 일을 찾을 수 없습니다."));

        todo.setCompleted(!todo.isCompleted());
        return new TodoDto(todoRepository.save(todo));
    }
}
