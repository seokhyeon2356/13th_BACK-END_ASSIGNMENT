package com.example.LikeLionWeek4.repository;

import com.example.LikeLionWeek4.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findByChatRoomId(Long chatRoomId);
}
