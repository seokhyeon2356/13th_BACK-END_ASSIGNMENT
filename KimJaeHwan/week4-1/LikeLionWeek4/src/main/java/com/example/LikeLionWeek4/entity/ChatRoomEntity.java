package com.example.LikeLionWeek4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomName;

    @OneToMany(mappedBy = "chatRooms", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserChatRoomEntity> userChatRooms = new HashSet<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatEntity> chats = new HashSet<>();

    public ChatRoomEntity(String roomName){
        this.roomName = roomName;
    }
}
