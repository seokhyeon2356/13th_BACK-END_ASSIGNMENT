package com.domain.likelionweek4.repository;

import com.domain.likelionweek4.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    List<ChatRoomEntity> findByUserChatRoomEntities_UserEntity_Username(String username);
}
