package com.acycycy.LikeLionWeek2.controller;

import com.acycycy.LikeLionWeek2.dto.ChatDto;
import com.acycycy.LikeLionWeek2.repository.ChatRepository;
import com.acycycy.LikeLionWeek2.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatRepository chatRepository;

    @PostMapping
    public ResponseEntity<ChatDto> sendMessage(
            @RequestBody Map<String,Object> body,
            @AuthenticationPrincipal UserDetails principal
    ) {
        Long roomId = ((Number) body.get("roomId")).longValue();
        String msg = (String) body.get("message");
        ChatDto dto = chatService.sendMessage(roomId, principal.getUsername(), msg);
        return ResponseEntity.status(201).body(dto);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markRead(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails principal
    ) {
        chatService.markAsRead(id, principal.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/room/{roomId}")
    public List<ChatDto> listChats(
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserDetails principal
    ) {
        return chatRepository.findByRoomIdOrderBySentAtAsc(roomId).stream()
                .map(c -> ChatDto.fromEntity(c, principal.getUsername()))
                .collect(Collectors.toList());
    }
}
