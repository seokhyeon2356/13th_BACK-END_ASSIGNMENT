package com.example.week4.Service;

import com.example.week4.Dto.ChatMessageRequestDto;
import com.example.week4.Dto.ChatMessageResponseDto;
import com.example.week4.Entity.ChatEntity;
import com.example.week4.Entity.ChatRoomEntity;
import com.example.week4.Entity.UserEntity;
import com.example.week4.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository, ChatRoomRepository chatRoomRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    // 메시지 전송
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto chatDto) {
        UserEntity sender = userRepository.findById(chatDto.getSenderId()).
                orElseThrow(() -> new EntityNotFoundException("사용자 없음 : " + chatDto.getSenderId()));
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatDto.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException("채팅방 없음 : " + chatDto.getChatRoomId()));

        ChatEntity newChat = new ChatEntity(chatDto.getContent(), sender, chatRoom);
        ChatEntity savedChat = chatRepository.save(newChat);

        return new ChatMessageResponseDto(
                savedChat.getId(), savedChat.getSender().getId(),
                savedChat.getSender().getUsername(), savedChat.getChatRoom().getId(),
                savedChat.getContent(), savedChat.getTimestamp(), savedChat.isRead()
        );
    }

    // 특정 채팅방의 메시지 목록 조회
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> getMessageByChatRoom(Long chatRoomId) {
        List<ChatEntity> chats = chatRepository.findByChatRoomId(chatRoomId);

        return chats.stream()
                .map(chat-> new ChatMessageResponseDto(
                        chat.getId(), chat.getSender().getId(), chat.getSender().getUsername(),
                        chat.getChatRoom().getId(), chat.getContent(), chat.getTimestamp(), true
                )).collect(Collectors.toList());
    }

    // 특정 메시지 ID로 삭제
    public void deleteMessage(Long chatId) {
        if(!chatRepository.existsById(chatId)) throw new EntityNotFoundException("채팅 없음 : " + chatId);
        chatRepository.deleteById(chatId);
    }
}
