package com.ul88.likelionweek5.Service;

import com.ul88.likelionweek5.Dto.ChatMessageResponseDto;
import com.ul88.likelionweek5.Dto.ChatRoomCreationRequestDto;
import com.ul88.likelionweek5.Dto.ChatRoomDetailsResponseDto;
import com.ul88.likelionweek5.Dto.ChatRoomResponseDto;
import com.ul88.likelionweek5.Entity.ChatRoomEntity;
import com.ul88.likelionweek5.Entity.UserChatRoomEntity;
import com.ul88.likelionweek5.Entity.UserEntity;
import com.ul88.likelionweek5.Repository.ChatRoomRepository;
import com.ul88.likelionweek5.Repository.UserChatRoomRepository;
import com.ul88.likelionweek5.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository, UserChatRoomRepository userChatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.userChatRoomRepository = userChatRoomRepository;
    }

    public ChatRoomResponseDto createChatRoom(ChatRoomCreationRequestDto roomDto){
        ChatRoomEntity newRoom = new ChatRoomEntity(roomDto.getRoomName());
        ChatRoomEntity savedRoom = chatRoomRepository.save(newRoom);

        return new ChatRoomResponseDto(savedRoom.getId(), savedRoom.getRoomName());
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> getAllChatRoom(){
        List<ChatRoomEntity> rooms = chatRoomRepository.findAll();
        return rooms.stream()
                .map(room -> new ChatRoomResponseDto(room.getId(), room.getRoomName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChatRoomDetailsResponseDto getChatRoomDetails(Long roomId){
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

    public void deleteChatRoom(Long roomId){
        if(!chatRoomRepository.existsById(roomId)) {
            throw new EntityNotFoundException("채팅방 없음: " + roomId);
        }
        chatRoomRepository.deleteById(roomId);
    }

    public void addUserToChatRoom(Long roomId, Long userId){
        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("채팅방 없음: " + roomId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음: " + userId));

        userChatRoomRepository.findByUserIdAndChatroomId(userId, roomId)
                .ifPresent(ucr -> {
                   throw new IllegalArgumentException("사용자 " + userId + "는 이미" + roomId + "에 있습니다.");
                });
        UserChatRoomEntity userChatRoom = new UserChatRoomEntity(user, room);
        userChatRoomRepository.save(userChatRoom);
    }

    public void removeUserFromChatRoom(Long roomId, Long userId){
        UserChatRoomEntity userChatRoom = userChatRoomRepository.findByUserIdAndChatroomId(userId,
roomId).orElseThrow(() -> new EntityNotFoundException("사용자 " + userId + "는" + roomId + "에 없습니다."));
    userChatRoomRepository.delete(userChatRoom);

    }

    public List<ChatRoomEntity> getChatRoomsForUser(String username) {
        return userChatRoomRepository.findChatRoomsByUsername(username);
    }
}
