package com.example.LikeLionWeek4.controller;

import com.example.LikeLionWeek4.dto.ChatMessageRequestDto;
import com.example.LikeLionWeek4.dto.ChatMessageResponseDto;
import com.example.LikeLionWeek4.service.ChatService;
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
    @PostMapping
    public ResponseEntity<ChatMessageResponseDto> sendMessage(@RequestBody ChatMessageRequestDto chatDto) {
        try {
            ChatMessageResponseDto sendMessage = chatService.sendMessage(chatDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(sendMessage);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ChatMessageResponseDto>> getMessageByChatRoom(@PathVariable Long roomId) {
        try {
            List<ChatMessageResponseDto> messages = chatService.getMessageByChatRoom(roomId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long chatId) {
        try {
            chatService.deleteMessage(chatId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/read/{chatId}/{userId}")
    public ResponseEntity<String> readMessage(@PathVariable Long chatId, @PathVariable Long userId){
        chatService.checkMessageRead(chatId, userId);
        return ResponseEntity.ok("readChat");
    }
    @GetMapping("/unread/{roomId}/{chatId}")
    public ResponseEntity<Long> unreadCount(@PathVariable Long roomId, @PathVariable Long chatId){
        return ResponseEntity.ok(chatService.unreadCount(roomId, chatId));
    }
}
