package com.example.week4.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chatrooms")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"userChatRooms", "chats"})

public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 값 증가
    private Long id;

    @Column(nullable = false)   // null 안댐
    private String roomName;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserChatRoomEntity> userChatRooms = new HashSet<>();

    // 채팅에서 오간 기록들
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatEntity> chats = new HashSet<>();

    public ChatRoomEntity(String roomName) {
        this.roomName = roomName;
    }
}
