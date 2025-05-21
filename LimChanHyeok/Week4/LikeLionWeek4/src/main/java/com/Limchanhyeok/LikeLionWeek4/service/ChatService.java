package com.Limchanhyeok.LikeLionWeek4.service;

import com.Limchanhyeok.LikeLionWeek4.dto.ChatMessageRequestDto;
import com.Limchanhyeok.LikeLionWeek4.entity.ChatEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.ChatReadEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.ChatRoomEntity;
import com.Limchanhyeok.LikeLionWeek4.entity.UserEntity;
import com.Limchanhyeok.LikeLionWeek4.repository.ChatReadRepository;
import com.Limchanhyeok.LikeLionWeek4.repository.ChatRepository;
import com.Limchanhyeok.LikeLionWeek4.repository.ChatRoomRepository;
import com.Limchanhyeok.LikeLionWeek4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatReadRepository chatReadRepository;

    public ChatEntity sendMessage(ChatMessageRequestDto dto) {
        UserEntity sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        ChatRoomEntity chatRoom = chatRoomRepository.findById(dto.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        ChatEntity chat = new ChatEntity();
        chat.setContent(dto.getContent());
        chat.setSender(sender);
        chat.setChatRoom(chatRoom);

        return chatRepository.save(chat);
    }

    public List<ChatEntity> getMessagesInRoom(Long chatRoomId) {
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방 없음"));
        return chatRepository.findByChatRoom(chatRoom);
    }

    public void markAsRead(Long chatId, Long userId) {
        ChatEntity chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("메시지 없음"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        boolean alreadyRead = chatReadRepository.existsByChatAndReader(chat, user);
        if (!alreadyRead) {
            chatReadRepository.save(new ChatReadEntity(null, chat, user, LocalDateTime.now()));
        }
    }
}
