package com.ul88.likelionweek5.Service;

import com.ul88.likelionweek5.Dto.ChatMessageRequestDto;
import com.ul88.likelionweek5.Dto.ChatMessageResponseDto;
import com.ul88.likelionweek5.Entity.ChatEntity;
import com.ul88.likelionweek5.Entity.ChatRoomEntity;
import com.ul88.likelionweek5.Entity.UserEntity;
import com.ul88.likelionweek5.Repository.ChatRepository;
import com.ul88.likelionweek5.Repository.ChatRoomRepository;
import com.ul88.likelionweek5.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository, ChatRoomRepository
chatRoomRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    //메시지 전송
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto chatDto) {
        UserEntity sender = userRepository.findById(chatDto.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음: " + chatDto.getSenderId()));
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatDto.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException("채팅방 없음: " + chatDto.getChatRoomId()));

        ChatEntity newChat = new ChatEntity(chatDto.getContent(), sender, chatRoom);
        ChatEntity savedChat = chatRepository.save(newChat);

        return new ChatMessageResponseDto(
                savedChat.getId(), savedChat.getSender().getId(),
                savedChat.getSender().getUsername(), savedChat.getChatRoom().getId(),
                savedChat.getContent(), savedChat.getTimestamp()
        );
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> getMessagesByChatRoom(Long chatRoom){

        List<ChatEntity> chats = chatRepository.findByChatroomId(chatRoomId);

        return chats.stream()
                .map(chat -> new ChatMessageResponseDto(
                        chat.getId(), chat.getSender().getId(), chat.getSender().getUsername(),
                        chat.getChatRoom().getId(), chat.getContent(), chat.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    public void deleteMessage(Long chatId){
        if(!chatRepository.existsById(chatId))
            throw new EntityNotFoundException("채팅 없음: " + chatId);
        chatRepository.deleteById(chatId);
    }

    @Transactional
    public void markAsRead(Long chatId, String username) {
        ChatEntity chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("메세지 없음"));

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        chat.getReadUsers().add(user);
        chatRepository.save(chat);
    }

    public int countUnread(Long chatId, int totalParticipants) {
        ChatEntity chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("메세지 없음"));
        return totalParticipants - chat.getReadUsers().size();
    }
}
