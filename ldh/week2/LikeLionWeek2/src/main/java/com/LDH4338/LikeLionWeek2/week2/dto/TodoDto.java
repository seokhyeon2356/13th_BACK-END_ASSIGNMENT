package com.LDH4338.LikeLionWeek2.week2.dto;

import com.LDH4338.LikeLionWeek2.week2.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private Long id;
    private String content;
    private boolean completed;

    //Entity -> Dto
    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.content = todo.getContent();
        this.completed = todo.isCompleted();
    }

    //Dto -> Entity
    public Todo toEntity() {
        Todo todo = new Todo();
        todo.setContent(this.content);
        todo.setCompleted(this.isCompleted());
        return todo;
    }
}
