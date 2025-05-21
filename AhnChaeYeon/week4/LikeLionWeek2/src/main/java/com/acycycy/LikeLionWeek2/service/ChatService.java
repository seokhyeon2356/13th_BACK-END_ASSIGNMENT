package com.acycycy.LikeLionWeek2.service;

import com.acycycy.LikeLionWeek2.dto.ChatDto;
import com.acycycy.LikeLionWeek2.entity.ChatEntity;
import com.acycycy.LikeLionWeek2.entity.RoomEntity;
import com.acycycy.LikeLionWeek2.entity.UserEntity;
import com.acycycy.LikeLionWeek2.repository.ChatRepository;
import com.acycycy.LikeLionWeek2.repository.RoomRepository;
import com.acycycy.LikeLionWeek2.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service @RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatDto sendMessage(Long roomId, String username, String message) {
        RoomEntity room = roomRepository.findById(roomId).orElseThrow();
        UserEntity sender = userRepository.findByUsername(username).orElseThrow();
        ChatEntity chat = ChatEntity.builder()
                .room(room)
                .sender(sender)
                .message(message)
                .sentAt(LocalDateTime.now())
                .build();
        ChatEntity saved = chatRepository.save(chat);
        return ChatDto.fromEntity(saved, username);
    }

    @Transactional
    public void markAsRead(Long chatId, String username) {
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow();
        UserEntity me = userRepository.findByUsername(username).orElseThrow();
        chat.getReadBy().add(me);
    }
}
