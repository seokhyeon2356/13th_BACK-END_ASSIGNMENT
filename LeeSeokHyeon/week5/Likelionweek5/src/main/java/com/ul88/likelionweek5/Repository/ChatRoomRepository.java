package com.ul88.likelionweek5.Repository;

import com.ul88.likelionweek5.Entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    Optional<ChatRoomEntity> findByRoomName(String roomName);

    List<ChatRoomEntity> findByUsersUsername(String username);
}