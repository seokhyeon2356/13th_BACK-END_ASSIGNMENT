package com.Limchanhyeok.LikeLionWeek4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequestDto {
    private Long chatRoomId;
    private Long senderId;
    private String content;
}
