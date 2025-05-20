package com.acycycy.LikeLionWeek2.service;

import com.acycycy.LikeLionWeek2.dto.RoomDto;
import com.acycycy.LikeLionWeek2.dto.CreateRoomRequest;
import com.acycycy.LikeLionWeek2.entity.RoomEntity;
import com.acycycy.LikeLionWeek2.entity.RoomMemberEntity;
import com.acycycy.LikeLionWeek2.entity.UserEntity;
import com.acycycy.LikeLionWeek2.repository.RoomRepository;
import com.acycycy.LikeLionWeek2.repository.RoomMemberRepository;
import com.acycycy.LikeLionWeek2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final UserRepository userRepository;

    public List<RoomDto> getRoomsForUser(String username) {
        return roomMemberRepository.findRoomsByUsername(username).stream()
                .map(RoomDto::fromEntity)
                .toList();
    }

    @Transactional
    public RoomDto createRoom(String name, String ownerUsername) {
        UserEntity owner = userRepository.findByUsername(ownerUsername).orElseThrow();
        RoomEntity room = RoomEntity.builder()
                .name(name)
                .owner(owner)
                .build();
        RoomEntity saved = roomRepository.save(room);
        // 생성자 자동 멤버 등록
        roomMemberRepository.save(RoomMemberEntity.builder()
                .room(saved).user(owner).build());
        return RoomDto.fromEntity(saved);
    }

    @Transactional
    public void addMember(Long roomId, String username) {
        RoomEntity room = roomRepository.findById(roomId).orElseThrow();
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        roomMemberRepository.save(RoomMemberEntity.builder()
                .room(room).user(user).build());
    }
}
