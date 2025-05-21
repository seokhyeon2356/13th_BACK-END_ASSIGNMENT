package com.acycycy.LikeLionWeek2.controller;

import com.acycycy.LikeLionWeek2.dto.RegisterDto;
import com.acycycy.LikeLionWeek2.dto.UserDto;
import com.acycycy.LikeLionWeek2.entity.UserEntity;
import com.acycycy.LikeLionWeek2.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterDto dto) {
        UserEntity created = userService.register(dto);
        UserDto response = UserDto.builder()
                .username(created.getUsername())
                .build();
        return ResponseEntity.ok(response);
    }
}
