package likelionweek4.week4.repository;

import likelionweek4.week4.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    List<ChatEntity> findByChatRoomId(Long chatRoomId);

    Optional<ChatEntity> findByIdAndChatRoomId(Long chatId, Long chatRoomId);
}
