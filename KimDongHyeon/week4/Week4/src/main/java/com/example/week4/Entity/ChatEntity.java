package com.example.week4.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"sender", "chatRoom"})

public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값 증가
    private Long id;

    @Column(nullable = false)   // null 허용 안함
    private String content;

    private LocalDateTime timestamp;    // 메시지 보낸 시간

    // 메시지 하나는 한명의 유저가 보냄
    // FetchType.Laze : 필요할 때만 DB에서 유저 정보 불러옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)   // 외래 키 컬럼 이름은 sender_id
    private UserEntity sender;

    // 이 메시지가 어느 채팅방에서 나왔는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false) // 외래키는 chatroom_id
    private ChatRoomEntity chatRoom;

    @ColumnDefault("false")
    private boolean isRead; // 읽었나?

    public ChatEntity(String content, UserEntity sender, ChatRoomEntity chatRoom) {
        this.content = content;
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.timestamp = LocalDateTime.now();
    }
}
