package com.acycycy.LikeLionWeek2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "todos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
