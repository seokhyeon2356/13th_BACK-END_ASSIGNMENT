package com.Limchanhyeok.LikeLionWeek4.repository;

import com.Limchanhyeok.LikeLionWeek4.entity.ChatRoomEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.UserChatRoomEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoomEntity, Long> {

    List<UserChatRoomEntity> findByUser(UserEntity user);

    List<UserChatRoomEntity> findByChatRoom(ChatRoomEntity chatRoom);

    Optional<UserChatRoomEntity> findByUserAndChatRoom(UserEntity user, ChatRoomEntity chatRoom);
}
