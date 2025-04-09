package com.LimChanHyeok.LikeLionWeek2.repository;

import com.LimChanHyeok.LikeLionWeek2.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,Long> {
}
