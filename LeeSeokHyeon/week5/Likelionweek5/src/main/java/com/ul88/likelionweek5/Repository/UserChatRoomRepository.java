package com.ul88.likelionweek5.Repository;

import com.ul88.likelionweek5.Entity.ChatRoomEntity;
import com.ul88.likelionweek5.Entity.UserChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoomEntity, Long> {
    @Query("SELECT ucr.chatroom FROM UserChatRoomEntity ucr WHERE ucr.user.username = :username")
    List<ChatRoomEntity> findChatRoomsByUsername(@Param("username") String username);
}


