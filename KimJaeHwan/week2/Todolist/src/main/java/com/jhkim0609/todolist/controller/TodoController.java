package com.jhkim0609.todolist.controller;

import com.jhkim0609.todolist.dto.TodoDto;
import com.jhkim0609.todolist.entity.TodoEntity;
import com.jhkim0609.todolist.repository.TodoEntityRepository;
import com.jhkim0609.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoEntityRepository todoEntityRepository;
    private final TodoService todoService;
    @GetMapping("Todo")
    public List<TodoEntity> getAllTodo(){
        return todoEntityRepository.findAll();
    }
    @PostMapping("Todo/add")
    public String add(@RequestBody TodoDto todoDto) {
        return todoService.addTodo(todoDto);
    }
    @PatchMapping("Todo/{id}")
    public String updateTodo(@PathVariable Long id, @RequestBody TodoDto todoDto){
        return todoService.changeTodo(id, todoDto);
    }
    @PatchMapping("Todo/Completed/{id}")
    public String updateCompleted(@PathVariable Long id){
        return todoService.changeCompleted(id);
    }
    @DeleteMapping("Todo/Delete/{id}")
    public String deleteTodo(@PathVariable Long id){
        return todoService.delTodo(id);
    }
}
