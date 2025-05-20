package likelionweek4.week4.repository;

import likelionweek4.week4.entity.UserChatRoomEntity;
import likelionweek4.week4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRoomEntity, Long> {

    Optional<UserChatRoomEntity> findByUserIdAndChatRoomId(Long userId, Long chatRoomId);

    List<UserChatRoomEntity> findByUser(UserEntity user);
}
