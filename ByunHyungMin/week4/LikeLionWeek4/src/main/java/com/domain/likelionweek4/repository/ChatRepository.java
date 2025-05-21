package com.domain.likelionweek4.repository;

import com.domain.likelionweek4.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
}
