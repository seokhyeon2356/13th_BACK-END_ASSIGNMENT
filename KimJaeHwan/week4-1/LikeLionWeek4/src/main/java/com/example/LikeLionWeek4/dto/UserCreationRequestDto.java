package com.example.LikeLionWeek4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//페스워드 추가
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequestDto {
    private String username;
    private String password;
}
