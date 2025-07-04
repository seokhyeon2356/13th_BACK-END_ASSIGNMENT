package com.example.LikeLionWeek4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"sender", "chatRoom"})
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoomEntity chatRoom;

    //userEntity와 연결해 읽은사람을 set에 추가
    @OneToMany(mappedBy = "userChats", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserEntity> userChats = new HashSet<>();

    public ChatEntity(String content, UserEntity sender, ChatRoomEntity chatRoom){
        this.content = content;
        this.sender = sender;
        this.chatRoom = chatRoom;
    }
}