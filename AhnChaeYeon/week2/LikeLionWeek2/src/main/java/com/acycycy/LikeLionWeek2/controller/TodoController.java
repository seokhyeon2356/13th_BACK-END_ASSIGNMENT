package com.acycycy.LikeLionWeek2.controller;

import com.acycycy.LikeLionWeek2.dto.TodoDto;
import com.acycycy.LikeLionWeek2.entity.TodoEntity;
import com.acycycy.LikeLionWeek2.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<TodoEntity> findAll() {
        return todoService.findAll();
    }

    @PostMapping
    public TodoEntity create(@RequestBody TodoDto dto) {
        return todoService.create(dto);
    }

    @PutMapping("/{id}")
    public TodoEntity update(@PathVariable Long id, @RequestBody TodoDto dto) {
        return todoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoService.delete(id);
    }

    @PatchMapping("/{id}/complete")
    public TodoEntity markComplete(@PathVariable Long id) {
        return todoService.markComplete(id);
    }

    @PatchMapping("/{id}/revert")
    public TodoEntity revert(@PathVariable Long id) {
        return todoService.revert(id);
    }
}
