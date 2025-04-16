package com.example.week2.service;

import com.example.week2.dto.TodoDto;
import com.example.week2.entity.TodoEntity;
import com.example.week2.repository.TodoEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoEntityRepository todoEntityRepository;

    public String addTodo(TodoDto todoDto) {
        todoEntityRepository.save(todoDto.toEntity());
        return "할 것 : " + todoDto.getWhatToDo() + " 추가";
    }

    public String updateToDo(Long id, TodoDto todoDto) {    // 수정하는 메서드
        Optional<TodoEntity> entity = todoEntityRepository.findById(id);
        if (entity.isPresent()) {
            TodoEntity todoEntity = entity.get();   // entity를 선언하여 기존의 entity 저장
            todoEntity.SetToDo(todoDto.getWhatToDo());  // 수정하고자 하는 값 입력
            todoEntityRepository.save(todoEntity);  // 수정된 entity 저장
            return "할 일 ID: " + id + " 가 수정되었습니다.";
        }
        else { return "할 일 ID: " + id + " 를 찾을 수 없습니다."; }
    }

    public String exchangeDone(Long id) {
        Optional<TodoEntity> entity = todoEntityRepository.findById(id);
        if(entity.isPresent()) {
            TodoEntity todoEntity = entity.get();
            String status = todoEntity.getDone();

            String newStatus = status.equals("할일") ? "완료" : "할일";   // 할일이라면 완료로 완료라면 할일으로 변경
            todoEntity.setDone(newStatus);
            todoEntityRepository.save(todoEntity);
            return "변경되었습니다.";
        }
        else { return "id가 존재하지 않습니다."; }
    }


    public String delete(Long id) {
        Optional<TodoEntity> entity = todoEntityRepository.findById(id);    // id에 해당하는 위치 찾기
        if (entity.isPresent()) {   // 있다면 삭제
            todoEntityRepository.delete(entity.get());  // delete로 수정
            return id + "에 해당하는 할일이 삭제되었습니다.";
        }
        else {  // 없으면 없다고 반환
            return id + "에 해당하는 할일이 존재하지 않습니다.";
        }
    }
}
