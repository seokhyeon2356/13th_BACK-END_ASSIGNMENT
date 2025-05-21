package com.ul88.likelionweek5.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_chatroom", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "chatroom_id"})
})
public class UserChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id",nullable = false)
    private ChatRoomEntity chatroom;
}
