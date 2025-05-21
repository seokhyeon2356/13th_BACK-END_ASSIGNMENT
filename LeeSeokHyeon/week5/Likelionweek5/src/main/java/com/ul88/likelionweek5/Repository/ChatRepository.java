package com.ul88.likelionweek5.Repository;

import com.ul88.likelionweek5.Entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findByChatroomId(Long chatroomId);
}
