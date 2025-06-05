package com.example.week4.Dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChatRoomDetailResponseDto {
    private Long id;
    private String roomName;
    private List<ChatMessageResponseDto> messages;

    public ChatRoomDetailResponseDto(Long id, String roomName, List<ChatMessageResponseDto> messages) {
        this.id = id;
        this.roomName = roomName;
        this.messages = messages;
    }
}
