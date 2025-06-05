package com.example.week4.Dto;

import lombok.*;

@Data
@NoArgsConstructor
public class ChatMessageRequestDto {
    private Long senderId;
    private Long chatRoomId;
    private String content;
}
