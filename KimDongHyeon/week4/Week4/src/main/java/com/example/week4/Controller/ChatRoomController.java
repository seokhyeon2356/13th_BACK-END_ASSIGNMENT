package com.example.week4.Controller;

import com.example.week4.Dto.ChatRoomCreationRequestDto;
import com.example.week4.Dto.ChatRoomDetailResponseDto;
import com.example.week4.Dto.ChatRoomResponseDto;
import com.example.week4.Service.*;
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

    // POST /api/chatrooms : 채팅방 생성
    @PostMapping
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomCreationRequestDto roomDto) {
        try {
            ChatRoomResponseDto createdRoom = chatRoomService.createChatRoom(roomDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // GET /api/chatrooms : 전체 채팅방 목록 조회
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDto>> getAllChatRooms() {
        List<ChatRoomResponseDto> rooms = chatRoomService.getAllChatRooms();
        return ResponseEntity.ok(rooms);
    }

    // GET /api/chatrooms/{roomId} : 특정 채팅방 상세 조회 (메시지 포함)
    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoomDetailResponseDto> getChatRoomDetails(@PathVariable Long roomId) {
        try {
            ChatRoomDetailResponseDto roomDetails = chatRoomService.getChatRoomDetails(roomId);
            return ResponseEntity.ok(roomDetails);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/chatrooms/{roomId} : 특정 채팅방 Id로 삭제
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Long roomId) {
        try {
            chatRoomService.deleteChatRoom(roomId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/chatrooms/{roomId}/users/{userId} : 채팅방에 사용자 추가
    @PostMapping("/{roomId}/users/{userId}")
    public ResponseEntity<Void> addUserToChatRoom(@PathVariable Long roomId, @PathVariable Long userId) {
        try {
            chatRoomService.addUserToChatRoom(roomId, userId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();   // 이미 있는 경우
        }
    }
}
