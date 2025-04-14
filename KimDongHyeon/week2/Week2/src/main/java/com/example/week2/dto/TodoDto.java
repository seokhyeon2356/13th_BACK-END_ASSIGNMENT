package com.example.week2.dto;

import com.example.week2.entity.TodoEntity;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private String WhatToDo;
    private String done;

    public static TodoDto fromEntity(TodoEntity entity) {
        return TodoDto.builder()
                .WhatToDo(entity.getWhatToDo())
                .done(entity.getDone())
                .build();
    }

    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .whatToDo(WhatToDo)
                .done(done)
                .build();
    }
}
