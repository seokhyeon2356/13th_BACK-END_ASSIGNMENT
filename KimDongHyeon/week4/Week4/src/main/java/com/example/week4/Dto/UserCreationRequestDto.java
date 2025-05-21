package com.example.week4.Dto;

import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
public class UserCreationRequestDto {
    private String username;
    private String password;    // 비밀번호
}
