package com.example.week4.Dto;

import lombok.*;

@Data
@NoArgsConstructor
public class ChatRoomResponseDto {
    private Long id;
    private String roomName;

    public ChatRoomResponseDto(Long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }
}
