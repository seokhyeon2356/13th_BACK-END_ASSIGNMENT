package com.jhkim0609.todolist.service;

import com.jhkim0609.todolist.dto.TodoDto;
import com.jhkim0609.todolist.entity.TodoEntity;
import com.jhkim0609.todolist.repository.TodoEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoEntityRepository todoEntityRepository;

    public String addTodo(TodoDto todoDto) {
        todoEntityRepository.save(todoDto.toEntity());
        return "Todo List adding : " + todoDto.getTodo();
    }
    public String delTodo(Long id){
        todoEntityRepository.deleteById(id);
        return "Delete completed : " + id;
    }
    public String changeTodo(Long id, TodoDto todoDto) {
        TodoEntity todoEntity = todoEntityRepository.findById(id).orElseThrow(() -> new IllegalAccessError(id + "의 ToDo는 없습니다."));
        String lasttodo = todoEntity.getTodo();
        todoEntity.SetTodo(todoDto.getTodo());
        todoEntityRepository.save(todoEntity);
        return "Change ToDo completed\n" + lasttodo + " -> " + todoEntity.getTodo();
    }
    public String changeCompleted(Long id) {
        TodoEntity todoEntity = todoEntityRepository.findById(id).orElseThrow(() -> new IllegalAccessError(id + "의 ToDo는 없습니다."));
        if (todoEntity.getCompleted() == true) {
            todoEntity.setCompleted(false);
            return "ToDo conversion finished";
        } else todoEntity.setCompleted(true);
        return "Todo conversion unfinished";
    }
}