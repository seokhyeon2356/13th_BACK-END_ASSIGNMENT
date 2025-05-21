package com.domain.likelionweek4.controller;

import com.domain.likelionweek4.entity.ChatRoomEntity;
import com.domain.likelionweek4.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;


    @GetMapping("/my")
    public ResponseEntity<List<ChatRoomEntity>> getMyRooms(@RequestParam String username) {
        return ResponseEntity.ok(chatRoomService.findChatRoomsByUsername(username));
    }

}
