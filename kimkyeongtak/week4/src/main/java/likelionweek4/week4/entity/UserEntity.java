package likelionweek4.week4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"chatRooms", "chats"})
public class UserEntity {

    public UserEntity(String username) {
        this.username = username;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

//    @ManyToMany
//    @JoinTable(
//            name = "user_chatrooms",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "chatroom_id")
//    )
//    private Set<ChatRoomEntity> chatRooms = new HashSet<>();

    // 유저가 참여한 채팅방
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserChatRoomEntity> userChatRooms = new HashSet<>();

    // 유저가 보낸 메시지
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatEntity> chats = new HashSet<>();

    // 유저가 읽은 메시지
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserChatEntity> userChat = new HashSet<>();

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
