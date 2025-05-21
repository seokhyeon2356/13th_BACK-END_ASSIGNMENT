package com.domain.likelionweek4.service;

import com.domain.likelionweek4.entity.ChatEntity;
import com.domain.likelionweek4.entity.UserEntity;
import com.domain.likelionweek4.repository.ChatRepository;
import com.domain.likelionweek4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public void markAsRead(Long chatId, String username) {
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow();
        UserEntity user = userRepository.findByUsername(username);
        chat.getReadUsers().add(user);
        chatRepository.save(chat);
    }

    public int getReadCount(Long chatId) {
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow();
        return chat.getReadUsers().size();
    }
}
