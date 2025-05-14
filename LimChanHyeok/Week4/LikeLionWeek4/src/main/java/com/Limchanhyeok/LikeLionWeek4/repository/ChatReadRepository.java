package com.Limchanhyeok.LikeLionWeek4.repository;

import com.Limchanhyeok.LikeLionWeek4.entity.ChatEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.ChatReadEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatReadRepository extends JpaRepository<ChatReadEntity, Long> {

    boolean existsByChatAndReader(ChatEntity chat, UserEntity reader);

    long countByChat(ChatEntity chat);

    List<ChatReadEntity> findByReader(UserEntity reader);
}