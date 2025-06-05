package com.example.week4.Entity;

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
    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 (sql : Auto_Increment)
    private Long id;

    @Column(nullable = false, unique = true)    // null 불가, 중복 값 저장 불가
    private String username;

    // 유저가 속한 채팅방
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserChatRoomEntity> userChatRooms = new HashSet<>();

    // 유저가 보낸 채팅 메시지들
    // mappedBy = sender : ChatEntity 클래스에 있는 private UserEntity sender를 기준으로 양방향 연결
    // cascade = ALL : 유저 저장 / 삭제 할때 채팅도 같이 처리됨
    // orphanRemoval = true : 유저에서 채팅 제거하면 DB에서도 삭제됨
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatEntity> chats = new HashSet<>();

    private String password;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
