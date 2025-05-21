package com.Limchanhyeok.LikeLionWeek4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {
    private Long id;
    private String senderUsername;
    private String content;
    private LocalDateTime sentAt;
}
