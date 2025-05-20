package likelionweek4.week4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// user가 chat을 읽었는지 여부 확인 중간다리 테이블
@Entity
@Table(name = "user_chat")
@Getter
@Setter
@NoArgsConstructor
public class UserChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
