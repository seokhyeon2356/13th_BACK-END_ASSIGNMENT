package likelionweek4.week4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_ChatRoom", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "chatroom_id"})
})
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user", "chatRoom"})
public class UserChatRoomEntity {

    public UserChatRoomEntity(UserEntity user, ChatRoomEntity chatRoom) {
        this.user = user;
        this.chatRoom = chatRoom;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoomEntity chatRoom;
}
