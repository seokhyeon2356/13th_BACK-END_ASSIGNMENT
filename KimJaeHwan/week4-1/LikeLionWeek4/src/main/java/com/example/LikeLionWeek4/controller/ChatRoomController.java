package com.example.LikeLionWeek4.controller;

import com.example.LikeLionWeek4.dto.ChatRoomCreationRequestDto;
import com.example.LikeLionWeek4.dto.ChatRoomDetailsResponseDto;
import com.example.LikeLionWeek4.dto.ChatRoomResponseDto;
import com.example.LikeLionWeek4.service.ChatRoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }
    @PostMapping
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomCreationRequestDto roomDto) {
        try {
            ChatRoomResponseDto createdRoom = chatRoomService.createChatRoom(roomDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDto>> getAllChatRooms() {
        List<ChatRoomResponseDto> rooms = chatRoomService.getAllChatRooms();
        return ResponseEntity.ok(rooms);
    }
    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoomDetailsResponseDto> getChatRoomDetails(@PathVariable Long roomId) {
        try {
            ChatRoomDetailsResponseDto roomDetails = chatRoomService.getChatRoomDetails(roomId);
            return ResponseEntity.ok(roomDetails);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deletChatRoom(@PathVariable Long roomId) {
        try {
            chatRoomService.deleteChatRoom(roomId);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{roomId}/users/{userId}")
    public ResponseEntity<Void> addUserToChatRoom(@PathVariable Long roomId, @PathVariable Long userId) {
        try {
            chatRoomService.addUserToChatRoom(roomId, userId);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
