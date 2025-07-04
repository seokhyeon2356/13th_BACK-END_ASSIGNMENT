package com.example.week4.Repository;

import com.example.week4.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    Optional<ChatRoomEntity> findByRoomName(String roomName);
}
