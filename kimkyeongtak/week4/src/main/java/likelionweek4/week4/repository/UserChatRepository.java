package likelionweek4.week4.repository;

import likelionweek4.week4.entity.UserChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserChatRepository extends JpaRepository<UserChatEntity, Long> {

    boolean existByUserIdAndChatId(Long userId, Long chatId);
}
