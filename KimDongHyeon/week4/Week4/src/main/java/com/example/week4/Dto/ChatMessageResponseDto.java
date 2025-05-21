package com.example.week4.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {
    private Long id;
    private Long senderId;
    private String senderUsername;
    private Long chatRoomId;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;
}
