package com.acycycy.LikeLionWeek2.dto;

import com.acycycy.LikeLionWeek2.entity.TodoEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {

    private Long id;
    private String content;
    private boolean completed;
    private String username;

    public static TodoDto fromEntity(TodoEntity entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .completed(entity.isCompleted())
                .username(entity.getUser() != null ? entity.getUser().getUsername() : null)
                .build();
    }
}