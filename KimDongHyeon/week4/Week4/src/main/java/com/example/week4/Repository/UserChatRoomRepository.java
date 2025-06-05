package com.example.week4.Repository;

import com.example.week4.Dto.UserChatRoomDto;
import com.example.week4.Entity.UserChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoomEntity, Long> {
    Optional<UserChatRoomEntity> findByUserIdAndChatRoomId(Long userId, Long chatRoomId);

    @Query(
            value = "SELECT uc.chatroom_id, uc.user_id " +
                    "FROM users AS u " +
                    "LEFT JOIN user_chatroom AS uc ON u.id = uc.user_id " +
                    "WHERE u.id = :userId ",
            nativeQuery = true
    )
    List<UserChatRoomDto> findUserChatRoomByUserId(@Param("userId") Long userId);

}
