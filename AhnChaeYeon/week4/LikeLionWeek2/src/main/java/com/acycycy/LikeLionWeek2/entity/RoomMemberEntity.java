package com.acycycy.LikeLionWeek2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_member")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoomMemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
