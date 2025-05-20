package com.domain.likelionweek4.controller;

import com.domain.likelionweek4.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/read/{chatId}")
    public ResponseEntity<String> markRead(@PathVariable Long chatId, @RequestParam String username) {
        chatService.markAsRead(chatId, username);
        return ResponseEntity.ok("읽음 처리 완료");
    }

    @GetMapping("/read-count/{chatId}")
    public ResponseEntity<Integer> getReadCount(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatService.getReadCount(chatId));
    }
}
