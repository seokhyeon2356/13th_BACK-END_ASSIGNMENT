package com.jhkim0609.todolist.dto;

import com.jhkim0609.todolist.entity.TodoEntity;
import lombok.*;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TodoDto {
    private String Todo;
    private boolean completed;
    public static TodoDto fromEntity(TodoEntity entity){
        return TodoDto.builder().Todo(entity.getTodo()).completed(entity.getCompleted()).build();
    }
    public TodoEntity toEntity() {
        return TodoEntity.builder().Todo(Todo).completed(completed).build();
    }
}
