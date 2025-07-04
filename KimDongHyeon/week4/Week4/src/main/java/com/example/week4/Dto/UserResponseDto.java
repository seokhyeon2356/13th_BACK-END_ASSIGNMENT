package com.example.week4.Dto;

import com.example.week4.Entity.UserChatRoomEntity;
import lombok.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
}
