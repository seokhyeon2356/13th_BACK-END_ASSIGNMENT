package com.example.LikeLionWeek4.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChatRoomDetailsResponseDto {
    private Long id;
    private String roomName;
    private List<ChatMessageResponseDto> messages;
    public ChatRoomDetailsResponseDto(Long id, String roomName, List<ChatMessageResponseDto> messages){
        this.id = id;
        this.roomName = roomName;
        this.messages = messages;
    }
}