package com.domain.likelionweek4.service;

import com.domain.likelionweek4.entity.ChatRoomEntity;
import com.domain.likelionweek4.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoomEntity> findChatRoomsByUsername(String username) {
        return chatRoomRepository.findByUserChatRoomEntities_UserEntity_Username(username);
    }
}
