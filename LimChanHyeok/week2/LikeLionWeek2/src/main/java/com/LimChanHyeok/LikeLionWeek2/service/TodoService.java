package com.LimChanHyeok.LikeLionWeek2.service;

import com.LimChanHyeok.LikeLionWeek2.dto.TodoDto;
import com.LimChanHyeok.LikeLionWeek2.entity.TodoEntity;
import com.LimChanHyeok.LikeLionWeek2.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public String addTodo(TodoDto dto){
        todoRepository.save(dto.toEntity());
        return "투두 추가 완료: " + dto.getContent();
    }

    public String updateTodo(Long id, TodoDto dto){
        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 투두가 없습니다."));
        todo.changeContent(dto.getContent());
        todoRepository.save(todo);
        return "투두 수정 완료: " + id;
    }

    public String deleteTodo(Long id){
        todoRepository.deleteById(id);
        return "투두 삭제 완료: " + id;
    }

//    public String markAsCompleted(Long id){
//        TodoEntity todo = todoRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 투두가 없습니다."));
//        todo.setCompleted(true);
//        todoRepository.save(todo);
//        return "할 일을 완료로 전환했습니다: " + id;
//    }
//
//    public String markAsUncompleted(Long id){
//        TodoEntity todo = todoRepository.findById(id)
//                .orElseThrow(()-> new IllegalArgumentException("해당 Id의 투두가 없습니다."));
//        todo.setCompleted(false);
//        todoRepository.save(todo);
//        return "완료를 다시 할 일로 전환했습니다.: " + id;
//    }

    public String toggleCompleted(Long id) {
        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 투두가 없습니다."));

        todo.setCompleted(!todo.isCompleted()); // 상태 반전
        todoRepository.save(todo);

        return "투두 상태를 " + (todo.isCompleted() ? "완료" : "할 일") + "로 변경했습니다.";
    }


//    public List<TodoDto> getAllTodos(){
//        List<TodoEntity> entities = todoRepository.findAll();
//        List<TodoDto> todoDtoList = new ArrayList<>();
//        for(TodoEntity entity : entities){
//            todoDtoList.add(TodoDto.fromEntitiy(entity));
//        }
//        return todoDtoList;
//    }

    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(TodoDto::fromEntitiy)
                .toList();
    }


}
