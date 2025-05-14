package com.Limchanhyeok.LikeLionWeek4.repository;

import com.Limchanhyeok.LikeLionWeek4.entity.ChatEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findByChatRoom(ChatRoomEntity chatRoom);
}
