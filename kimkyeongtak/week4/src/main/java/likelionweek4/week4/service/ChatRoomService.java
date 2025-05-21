package likelionweek4.week4.service;

import jakarta.persistence.EntityNotFoundException;
import likelionweek4.week4.dto.ChatMessageResponseDto;
import likelionweek4.week4.dto.ChatRoomCreationRequestDto;
import likelionweek4.week4.dto.ChatRoomDetailsResponseDto;
import likelionweek4.week4.dto.ChatRoomResponseDto;
import likelionweek4.week4.entity.ChatRoomEntity;
import likelionweek4.week4.entity.UserChatRoomEntity;
import likelionweek4.week4.entity.UserEntity;
import likelionweek4.week4.repository.ChatRoomRepository;
import likelionweek4.week4.repository.UserChatRoomRepository;
import likelionweek4.week4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;

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
    public ChatRoomDetailsResponseDto getChatRoomDetails(Long roomId) {
        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("채팅방 없음: " + roomId));

        List<ChatMessageResponseDto> messages = room.getChats().stream()
                .map(chat -> new ChatMessageResponseDto(
                        chat.getId(), chat.getSender().getId(), chat.getSender().getUsername(),
                        chat.getChatRoom().getId(), chat.getContent(), chat.getTimestamp()
                ))
                .collect(Collectors.toList());

        return new ChatRoomDetailsResponseDto(room.getId(), room.getRoomName(), messages);
    }

    // 특정 채팅방 ID로 삭제
    public void deleteChatRoom(Long roomId) {
        if (!chatRoomRepository.existsById(roomId)) {
            throw new EntityNotFoundException("채팅방 없음: " + roomId);
        }
        chatRoomRepository.deleteById(roomId);
    }

    // 채팅방에 사용자 추가 메소드
    public void addUserToChatRoom(Long roomId, Long userId) {
        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("채팅방 없음: " + roomId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음: " + userId));

        userChatRoomRepository.findByUserIdAndChatRoomId(userId, roomId)
                .ifPresent(ucr -> {
                    throw new IllegalArgumentException("사용자 " + userId + " 는 이미 " + roomId + "에 있습니다.");
                });

        UserChatRoomEntity userChatRoom = new UserChatRoomEntity(user, room);
        userChatRoomRepository.save(userChatRoom);
    }

    // 채팅방에서 사용자 제거 메소드
    public void removeUserFromChatRoom(Long roomId, Long userId) {
        UserChatRoomEntity userChatRoom = userChatRoomRepository.findByUserIdAndChatRoomId(userId, roomId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 " + userId + " 는 " + roomId + "에 없습니다."));

        userChatRoomRepository.delete(userChatRoom);
    }

    // 유저의 채팅방 조회 메서드
    public List<ChatRoomResponseDto> getChatRoomsByUsername(String username) {
        UserEntity existUser = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        List<ChatRoomResponseDto> chatRoomResponseDtos = userChatRoomRepository.findByUser(existUser).stream()
                .map(userChatRoom -> new ChatRoomResponseDto(userChatRoom.getChatRoom().getId(), userChatRoom.getChatRoom().getRoomName()))
                .toList();
        return chatRoomResponseDtos;
    }
}
