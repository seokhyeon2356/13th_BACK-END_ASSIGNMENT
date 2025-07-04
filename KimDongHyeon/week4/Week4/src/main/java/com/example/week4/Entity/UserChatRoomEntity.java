package com.example.week4.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table (name = "user_chatroom", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "chatroom_id"})
})
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user", "chatRoom"})

public class UserChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ElementCollection
    @Column(name = "chatroom_id")
    private List<Long> userchatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoomEntity chatRoom;

    public UserChatRoomEntity(UserEntity user, ChatRoomEntity room) {
        this.user = user;
        this.chatRoom = room;
    }
}