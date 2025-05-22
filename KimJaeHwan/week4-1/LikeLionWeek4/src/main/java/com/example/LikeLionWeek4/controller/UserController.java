package com.example.LikeLionWeek4.controller;

import com.example.LikeLionWeek4.dto.ChatRoomResponseDto;
import com.example.LikeLionWeek4.dto.UserCreationRequestDto;
import com.example.LikeLionWeek4.dto.UserResponseDto;
import com.example.LikeLionWeek4.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreationRequestDto userDto) {
        try {
            UserResponseDto createdUser = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    //사용자 이름으로 모든 채팅방 가져오기
    @GetMapping("/{userName}")
    public ResponseEntity<Set<ChatRoomResponseDto>> getAllChatRooms(@RequestBody String userName){
        Set<ChatRoomResponseDto> chatrooms = userService.getAllRooms(userName);
        return ResponseEntity.ok(chatrooms);
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        try {
            UserResponseDto user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
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