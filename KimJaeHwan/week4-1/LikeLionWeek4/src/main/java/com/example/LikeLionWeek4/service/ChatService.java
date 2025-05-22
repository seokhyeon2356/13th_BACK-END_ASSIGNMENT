package com.example.LikeLionWeek4.service;

import com.example.LikeLionWeek4.dto.ChatMessageRequestDto;
import com.example.LikeLionWeek4.dto.ChatMessageResponseDto;
import com.example.LikeLionWeek4.entity.ChatEntity;
import com.example.LikeLionWeek4.entity.ChatRoomEntity;
import com.example.LikeLionWeek4.entity.UserEntity;
import com.example.LikeLionWeek4.repository.ChatRepository;
import com.example.LikeLionWeek4.repository.ChatRoomRepository;
import com.example.LikeLionWeek4.repository.UserRepository;
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
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto chatDto) {
        UserEntity sender = userRepository.findById(chatDto.getSenderId()).orElseThrow(() -> new EntityNotFoundException("사용자 없음: "+chatDto.getSenderId()));
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatDto.getChatRoomId()).orElseThrow(() -> new EntityNotFoundException("채팅방 없음: " + chatDto.getChatRoomId()));
        ChatEntity newChat = new ChatEntity(chatDto.getContent(), sender, chatRoom);
        ChatEntity savedChat = chatRepository.save(newChat);
        return new ChatMessageResponseDto(savedChat.getId(), savedChat.getSender().getId(), savedChat.getSender().getUsername(), savedChat.getChatRoom().getId(), savedChat.getContent(), savedChat.getTimestamp());
    }
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> getMessageByChatRoom(Long chatRoomId) {
        List<ChatEntity> chats = chatRepository.findByChatRoomId(chatRoomId);
        return chats.stream().map(chat -> new ChatMessageResponseDto(chat.getId(), chat.getSender().getId(), chat.getSender().getUsername(), chat.getChatRoom().getId(), chat.getContent(), chat.getTimestamp())).collect(Collectors.toList());
    }
    public void deleteMessage(Long chatId) {
        if(!chatRepository.existsById(chatId))
            throw new EntityNotFoundException("채팅 없음: "+chatId);
        chatRepository.deleteById(chatId);
    }
    public void checkMessageRead(Long chatId, Long userId){
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("채팅 없음: "+chatId));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저 없음: "+userId));
        if(!chat.getUserChats().contains(user)){
            chat.getUserChats().add(user);
            user.getUserChats().add(chat);
        }
    }
    public Long unreadCount(Long roomId, Long chatId){
        ChatRoomEntity room = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("채팅방 없음: "+roomId));
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow(()-> new EntityNotFoundException("채팅 없음: "+chatId));
        Long userCount = Long.valueOf(room.getUserChatRooms().size());
        Long readuserCount = Long.valueOf(chat.getUserChats().size());
        return userCount - readuserCount;
    }
}
