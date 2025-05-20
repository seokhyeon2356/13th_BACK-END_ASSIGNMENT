package com.acycycy.LikeLionWeek2.repository;

import com.acycycy.LikeLionWeek2.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findByRoomIdOrderBySentAtAsc(Long roomId);
}
