package com.example.week4.Controller;

import com.example.week4.Dto.ChatMessageRequestDto;
import com.example.week4.Dto.ChatMessageResponseDto;
import com.example.week4.Service.ChatService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // POST /api/chats : 메시지 전송 (Chat 엔티티 생성)
    @PostMapping
    public ResponseEntity<ChatMessageResponseDto> sendMessage(@RequestBody ChatMessageRequestDto chatDto) {
        try {
            ChatMessageResponseDto sendMessage = chatService.sendMessage(chatDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(sendMessage);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();   // 404 NOT FOUND (sender 또는 chatroom 없음)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/chats/room/{roomId} : 특정 채팅방의 메시지 목록 조회
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ChatMessageResponseDto>> getMessagesByChatRoom(@PathVariable Long roomId) {
        try {
            List<ChatMessageResponseDto> messages = chatService.getMessageByChatRoom(roomId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE /api/chats/{chatId} : 특정 메시지 ID로 삭제
    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long chatId) {
        try {
            chatService.deleteMessage(chatId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
