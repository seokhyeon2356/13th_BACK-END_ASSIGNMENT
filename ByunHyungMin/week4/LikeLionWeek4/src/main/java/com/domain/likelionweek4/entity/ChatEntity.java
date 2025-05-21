package com.domain.likelionweek4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime sentAt = LocalDateTime.now();

    @ManyToOne
    private ChatRoomEntity chatRoom;

    @ManyToOne
    private UserEntity sender;

    @ManyToMany
    private Set<UserEntity> readUsers = new HashSet<>();
}
