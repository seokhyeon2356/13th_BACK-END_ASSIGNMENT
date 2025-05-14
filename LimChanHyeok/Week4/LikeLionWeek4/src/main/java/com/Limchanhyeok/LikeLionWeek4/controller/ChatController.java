package com.Limchanhyeok.LikeLionWeek4.controller;

import com.Limchanhyeok.LikeLionWeek4.dto.ChatMessageRequestDto;
import com.Limchanhyeok.LikeLionWeek4.entity.ChatEntity;
import com.Limchanhyeok.LikeLionWeek4.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatEntity> sendMessage(@RequestBody ChatMessageRequestDto dto) {
        return ResponseEntity.ok(chatService.sendMessage(dto));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ChatEntity>> getMessagesInRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatService.getMessagesInRoom(roomId));
    }

    @PostMapping("/{chatId}/read")
    public ResponseEntity<String> markChatAsRead(@PathVariable Long chatId,
                                                 @RequestParam Long userId) {
        chatService.markAsRead(chatId, userId);
        return ResponseEntity.ok("읽음 처리 완료");
    }
}