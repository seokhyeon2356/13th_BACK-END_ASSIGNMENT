package com.Limchanhyeok.LikeLionWeek4.controller;

import com.Limchanhyeok.LikeLionWeek4.dto.ChatRoomCreationRequestDto;
import com.Limchanhyeok.LikeLionWeek4.dto.ChatRoomResponseDto;
import com.Limchanhyeok.LikeLionWeek4.entity.ChatRoomEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.UserEntity;
import com.Limchanhyeok.LikeLionWeek4.service.ChatRoomService;
import com.Limchanhyeok.LikeLionWeek4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ChatRoomEntity> createRoom(@RequestBody ChatRoomCreationRequestDto dto,
                                                     @RequestParam Long creatorId) {
        UserEntity creator = userService.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        return ResponseEntity.ok(chatRoomService.createRoom(dto, creator));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomEntity>> getAllRooms() {
        return ResponseEntity.ok(chatRoomService.getAllRooms());
    }

    @GetMapping("/my")
    public ResponseEntity<List<ChatRoomResponseDto>> getUserChatRooms(@RequestParam String username) {
        List<ChatRoomEntity> rooms = chatRoomService.getChatRoomsByUsername(username);
        List<ChatRoomResponseDto> response = rooms.stream()
                .map(room -> new ChatRoomResponseDto(room.getId(), room.getName()))
                .toList();

        return ResponseEntity.ok(response);
    }

}
