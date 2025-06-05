package com.example.LikeLionWeek4.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomResponseDto {
    private Long id;
    private String roomName;
    public ChatRoomResponseDto(Long id, String roomName){
        this.id = id;
        this.roomName = roomName;
    }
}
