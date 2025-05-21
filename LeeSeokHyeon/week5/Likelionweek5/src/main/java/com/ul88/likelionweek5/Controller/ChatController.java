package com.ul88.likelionweek5.Controller;

import com.ul88.likelionweek5.Dto.ChatMessageRequestDto;
import com.ul88.likelionweek5.Dto.ChatMessageResponseDto;
import com.ul88.likelionweek5.Service.ChatService;
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
            ChatMessageResponseDto sentMessage = chatService.sendMessage(chatDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ChatMessageResponseDto>> getMessages(@PathVariable Long roomId) {
        try {
            List<ChatMessageResponseDto> messages = chatService.getMessagesByChatRoom(roomId);
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

    @PostMapping("/{chatId}/read")
    public ResponseEntity<String> markAsRead(
            @PathVariable Long chatId,
            @RequestParam String username
    ) {
        chatService.markAsRead(chatId, username);
        return ResponseEntity.ok("읽음 처리 완료");
    }

    @GetMapping("/{chatId}/unread-count")
    public ResponseEntity<Integer> getUnreadCount(
            @PathVariable Long chatId,
            @RequestParam int totalParticipants
    ) {
        int unreadCount = chatService.countUnread(chatId, totalParticipants);
        return ResponseEntity.ok(unreadCount);
    }
}
