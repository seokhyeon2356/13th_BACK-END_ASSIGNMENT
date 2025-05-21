package com.example.week4.Dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor // 이걸 해야지 자동으로 받아줌
public class UserChatRoomDto {
    private Long user_id;
    private Long chatroom_id;
}