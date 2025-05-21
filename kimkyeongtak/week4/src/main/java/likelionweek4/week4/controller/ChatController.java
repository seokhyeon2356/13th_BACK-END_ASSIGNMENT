package likelionweek4.week4.controller;

import jakarta.persistence.EntityNotFoundException;
import likelionweek4.week4.dto.ChatMessageRequestDto;
import likelionweek4.week4.dto.ChatMessageResponseDto;
import likelionweek4.week4.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    // POST /api/chats : 메시지 전송 (Chat 엔티티 생성)
    @PostMapping
    public ResponseEntity<ChatMessageResponseDto> sendMessage(@RequestBody ChatMessageRequestDto chatDto) {
        try {
            ChatMessageResponseDto sentMessage = chatService.sendMessage(chatDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found (sender 또는 chatroom 없음)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/chats/room/{roomId} : 특정 채팅방의 메시지 목록 조회
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ChatMessageResponseDto>> getMessagesByChatRoom(@PathVariable Long roomId) {
        try {
            List<ChatMessageResponseDto> messages = chatService.getMessagesByChatRoom(roomId);
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

    // 읽음 처리
    @PostMapping("/chats/{chatId}/user/{userId}")
    public ResponseEntity<String> readMessage(@PathVariable Long chatId, @PathVariable Long userId) {
        chatService.markMessageAsRead(chatId, userId);
        return ResponseEntity.ok().body("success");
    }

    // 특정 채팅방에서 채팅의 읽지 않은 수
    @GetMapping("/chats/{chatId}/room/{roomId}")
    public ResponseEntity<Long> countUnread(@PathVariable Long chatId, @PathVariable Long roomId) {
        return ResponseEntity.ok(chatService.countUnreadMessages(chatId, roomId));
    }
}