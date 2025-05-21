package com.acycycy.LikeLionWeek2.controller;

import com.acycycy.LikeLionWeek2.dto.TodoDto;
import com.acycycy.LikeLionWeek2.dto.UserDto;
import com.acycycy.LikeLionWeek2.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/user/{username}")
    public List<TodoDto> getTodosByUsername(@PathVariable String username) {
        return todoService.getTodosByUsername(username);
    }

//    @GetMapping("/user")
//    public List<TodoDto> getTodosByUsernameAndCompletion(@RequestBody UserDto userDto) {
//        return todoService.getTodosByUsernameAndCompletion(userDto.getUsername(), userDto.isCompleted());
//    }

    @PostMapping("/user")
    public TodoDto createTodo(@RequestBody TodoDto dto) {
        return todoService.createTodo(dto);
    }

    @PatchMapping("/user/{id}")
    public TodoDto completeTodo(@PathVariable Long id) {
        return todoService.completeTodo(id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}