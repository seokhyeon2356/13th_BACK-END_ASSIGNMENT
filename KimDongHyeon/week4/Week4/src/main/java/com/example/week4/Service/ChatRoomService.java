package com.example.week4.Service;

import com.example.week4.Dto.*;
import com.example.week4.Entity.ChatEntity;
import com.example.week4.Entity.ChatRoomEntity;
import com.example.week4.Entity.UserChatRoomEntity;
import com.example.week4.Entity.UserEntity;
import com.example.week4.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;


    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository,
       UserChatRoomRepository userChatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.userChatRoomRepository = userChatRoomRepository;
    }

    // 새로운 채팅방 생성
    public ChatRoomResponseDto createChatRoom(ChatRoomCreationRequestDto roomDto) {
        ChatRoomEntity newRoom = new ChatRoomEntity(roomDto.getRoomName());
        ChatRoomEntity savedRoom = chatRoomRepository.save(newRoom);

        return new ChatRoomResponseDto(savedRoom.getId(), savedRoom.getRoomName());
    }

    // 전체 채팅방 목록 조회
    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> getAllChatRooms() {
        List<ChatRoomEntity> rooms = chatRoomRepository.findAll();
        return rooms.stream()
                .map(room -> new ChatRoomResponseDto(room.getId(), room.getRoomName()))
                .collect(Collectors.toList());
    }

    // 특정 채팅방 상세 조회 (메시지)
    @Transactional(readOnly = true)
    public ChatRoomDetailResponseDto getChatRoomDetails(Long roomId) {
        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("채팅방 없음 : " + roomId));

        List<ChatMessageResponseDto> messages = room.getChats().stream()
                .map(chat -> new ChatMessageResponseDto(
                        chat.getId(), chat.getSender().getId(), chat.getSender().getUsername(),
                        chat.getChatRoom().getId(), chat.getContent(), chat.getTimestamp(), chat.isRead()
                )).collect(Collectors.toList());

        return new ChatRoomDetailResponseDto(room.getId(), room.getRoomName(), messages);
    }

    // 특정 채팅방 ID로 삭제
    public void deleteChatRoom(Long roomId) {
        if (!chatRoomRepository.existsById(roomId)) {
            throw new EntityNotFoundException("채팅방 없음 : " + roomId);
        }
        chatRoomRepository.deleteById(roomId);
    }

    // 채팅방에 사용자 추가 메소드
    public void addUserToChatRoom(Long roomId, Long userId) {
        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("채팅방 없음 : " + roomId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("사용자 없음 : " + userId));

        userChatRoomRepository.findByUserIdAndChatRoomId(userId, roomId)
                .ifPresent(ucr -> {
                    throw new IllegalArgumentException("사용자 " + userId + "는 이미 " + roomId + "에 있습니다.");
                });
        UserChatRoomEntity userChatRoom = new UserChatRoomEntity(user, room);
        userChatRoomRepository.save(userChatRoom);
    }

    // 채팅방 사용자 제거 메소드
    public void removeUserFromChatRoom(Long roomId, Long userId) {
        UserChatRoomEntity userChatRoom = userChatRoomRepository.findByUserIdAndChatRoomId(userId, roomId)
            .orElseThrow(()-> new EntityNotFoundException("사용자 " + userId + "는 " + roomId + "에 없습니다."));
        userChatRoomRepository.delete(userChatRoom);
    }

    // 여기에 해야할거 같은데
}
