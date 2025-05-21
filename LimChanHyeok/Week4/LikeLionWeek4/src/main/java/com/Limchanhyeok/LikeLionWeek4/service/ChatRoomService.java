package com.Limchanhyeok.LikeLionWeek4.service;

import com.Limchanhyeok.LikeLionWeek4.dto.ChatRoomCreationRequestDto;
import com.Limchanhyeok.LikeLionWeek4.entity.ChatRoomEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.UserChatRoomEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.UserEntity;
import com.Limchanhyeok.LikeLionWeek4.repository.ChatRoomRepository;
import com.Limchanhyeok.LikeLionWeek4.repository.UserChatRoomRepository;
import com.Limchanhyeok.LikeLionWeek4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    public ChatRoomEntity createRoom(ChatRoomCreationRequestDto dto, UserEntity creator) {

        ChatRoomEntity room = new ChatRoomEntity();
        room.setName(dto.getName());
        ChatRoomEntity savedRoom = chatRoomRepository.save(room);

        // 방 만들면 자동으로 생성자 참여
        UserChatRoomEntity join = new UserChatRoomEntity();
        join.setUser(creator);
        join.setChatRoom(savedRoom);
        userChatRoomRepository.save(join);

        return savedRoom;
    }

    public List<ChatRoomEntity> getAllRooms() {
        return chatRoomRepository.findAll();
    }

    public List<UserChatRoomEntity> getUsersInRoom(ChatRoomEntity room) {
        return userChatRoomRepository.findByChatRoom(room);
    }

    public List<ChatRoomEntity> getChatRoomsByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        List<UserChatRoomEntity> userChatRooms = userChatRoomRepository.findByUser(user);

        return userChatRooms.stream()
                .map(UserChatRoomEntity::getChatRoom)
                .distinct()
                .toList();
    }
}