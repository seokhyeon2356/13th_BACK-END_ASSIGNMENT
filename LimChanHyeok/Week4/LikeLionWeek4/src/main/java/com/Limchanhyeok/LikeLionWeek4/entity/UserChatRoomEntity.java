package com.Limchanhyeok.LikeLionWeek4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_chatrooms",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "chatroom_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // N:1 관계 - User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // N:1 관계 - ChatRoom
    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoomEntity chatRoom;

    // 필요 시 추가 필드
    private LocalDateTime joinedAt = LocalDateTime.now();
}
