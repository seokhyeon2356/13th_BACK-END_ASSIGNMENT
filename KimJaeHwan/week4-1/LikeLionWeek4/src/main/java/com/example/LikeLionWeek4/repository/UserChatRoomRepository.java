package com.example.LikeLionWeek4.repository;

import com.example.LikeLionWeek4.entity.UserChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoomEntity, Long> {
    Optional<UserChatRoomEntity> findByUserIdAndChatRoomId(Long userId, Long chatRooId);
}
