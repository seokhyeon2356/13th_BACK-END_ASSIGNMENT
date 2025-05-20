package com.acycycy.LikeLionWeek2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterDto {
    @NotBlank private String username;
    @NotBlank private String password;
}
