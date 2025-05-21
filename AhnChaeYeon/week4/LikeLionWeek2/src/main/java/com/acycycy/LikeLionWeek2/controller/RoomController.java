package com.acycycy.LikeLionWeek2.controller;

import com.acycycy.LikeLionWeek2.dto.RoomDto;
import com.acycycy.LikeLionWeek2.dto.CreateRoomRequest;
import com.acycycy.LikeLionWeek2.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomDto> listRooms(@AuthenticationPrincipal UserDetails principal) {
        return roomService.getRoomsForUser(principal.getUsername());
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(
            @RequestBody CreateRoomRequest req,
            @AuthenticationPrincipal UserDetails principal
    ) {
        RoomDto room = roomService.createRoom(req.getName(), principal.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @PostMapping("/{roomId}/members")
    public ResponseEntity<Void> addMember(
            @PathVariable Long roomId,
            @RequestBody Map<String,String> body
    ) {
        roomService.addMember(roomId, body.get("username"));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
