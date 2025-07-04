package com.example.week4.Controller;

import com.example.week4.Dto.*;
import com.example.week4.Entity.UserChatRoomEntity;
import com.example.week4.Service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/users : 사용자 생성
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreationRequestDto userDto) {
        try {
            UserResponseDto createdUser = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // GET /api/users : 전체 사용자 목록 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET /api/users/{userId} : 특정 사용자 Id로 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserChatRoomDto>> getChatroomsByUserId(@PathVariable Long userId) {
        try {
            List<UserChatRoomDto> userChatrooms = userService.getChatRoom(userId);

            return ResponseEntity.ok(userChatrooms);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/users/{userId} : 특정 사용자 Id로 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
