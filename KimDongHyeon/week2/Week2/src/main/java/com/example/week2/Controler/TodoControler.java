package com.example.week2.Controler;

import com.example.week2.dto.TodoDto;
import com.example.week2.entity.TodoEntity;
import com.example.week2.repository.TodoEntityRepository;
import com.example.week2.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoControler {
    private final TodoService todoService;
    private final TodoEntityRepository todoEntityRepository;

    // Restful하게 수정
    @PostMapping("ToDo/add")
    public String add(@RequestBody TodoDto todoDto) { return todoService.addTodo(todoDto); }

    @GetMapping("ToDo")
    public List<TodoEntity> getAllTodo() {  // 모든 투두리스트를 확인하기
        return todoEntityRepository.findAll();
    }

    @PutMapping("ToDo/{id}")    // 투두리스트 수정하기
    public String update(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        return todoService.updateToDo(id, todoDto);
    }

    @PutMapping("ToDo/status/{id}")  // 할일 -> 완료 || 완료 -> 할일
    public String update(@PathVariable Long id) {
        return todoService.exchangeDone(id);
    }

    @DeleteMapping("ToDo/delete/{id}")   // 투두리스트 삭제하기
    public String delete(@PathVariable Long id) {
        return todoService.delete(id);
    }
}
