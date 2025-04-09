package com.LimChanHyeok.LikeLionWeek2.dto;

import com.LimChanHyeok.LikeLionWeek2.entity.TodoEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
    private String content;
    private boolean completed;

    public TodoEntity toEntity(){
        return TodoEntity.builder()
                .content(content)
                .completed(completed)
                .build();
    }

    public static TodoDto fromEntitiy(TodoEntity entity){
        return TodoDto.builder()
                .content(entity.getContent())
                .completed(entity.isCompleted())
                .build();
    }
}
