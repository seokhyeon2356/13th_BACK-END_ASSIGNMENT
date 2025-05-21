package com.ul88.likelionweek5.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomResponseDto {
    private String roomName;
    private Long id;

    public ChatRoomResponseDto(String roomName, Long id) {
        this.roomName = roomName;
        this.id = id;
    }
}
