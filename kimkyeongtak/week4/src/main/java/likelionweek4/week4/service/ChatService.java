package likelionweek4.week4.service;

import jakarta.persistence.EntityNotFoundException;
import likelionweek4.week4.dto.ChatMessageRequestDto;
import likelionweek4.week4.dto.ChatMessageResponseDto;
import likelionweek4.week4.entity.*;
import likelionweek4.week4.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRepository userChatRepository;

    // 메시지 전송
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto chatDto) {
        UserEntity sender = userRepository.findById(chatDto.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음 : " + chatDto.getSenderId()));

        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatDto.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException("채팅방 없음 : " + chatDto.getChatRoomId()));

        ChatEntity newChat = new ChatEntity(chatDto.getContent(), sender, chatRoom);
        ChatEntity savedChat = chatRepository.save(newChat);

        return new ChatMessageResponseDto(
                savedChat.getId(),
                savedChat.getSender().getId(),
                savedChat.getSender().getUsername(),
                savedChat.getChatRoom().getId(),
                savedChat.getContent(),
                savedChat.getTimestamp()
        );
    }

    // 특정 채팅방의 메시지 목록 조회
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> getMessagesByChatRoom(Long chatRoomId) {
        List<ChatEntity> chats = chatRepository.findByChatRoomId(chatRoomId);

        return chats.stream()
                .map(chat -> new ChatMessageResponseDto(
                        chat.getId(),
                        chat.getSender().getId(),
                        chat.getSender().getUsername(),
                        chat.getChatRoom().getId(),
                        chat.getContent(),
                        chat.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    // 특정 메시지 ID로 삭제
    public void deleteMessage(Long chatId) {
        if (!chatRepository.existsById(chatId)) {
            throw new EntityNotFoundException("삭제할 메시지 없음 : " + chatId);
        }

        chatRepository.deleteById(chatId);
    }


    // 메시지 읽음 처리
    public void markMessageAsRead(Long chatId, Long userId) {

        ChatEntity chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found: " + chatId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        // userChat에 존재하면 이미 읽은 것, 아니면 아직 읽지 않은 것
        if (!userChatRepository.existByUserIdAndChatId(userId, chatId)) {

            UserChatEntity readEntry = new UserChatEntity();
            readEntry.setChat(chat);
            readEntry.setUser(user);

            chat.getUserChats().add(readEntry);
            user.getUserChat().add(readEntry);

            userChatRepository.save(readEntry);
        }
    }

    // 특정 채팅방에서 유저가 읽지 않은 메시지 수 조회
    public long countUnreadMessages(Long chatId, Long chatRoomId) {

        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("ChatRoom not found: " + chatRoomId));

        ChatEntity chat = chatRepository.findByIdAndChatRoomId(chatId, chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found: " + chatId));

        // 채팅방에 존재하는 인원 수
        int totalUsers = chatRoom.getUserChatRooms().size();

        // 채팅방에서 해당 채팅을 읽은 사람 수
        int readUsers = chat.getUserChats().size();

        return totalUsers - readUsers;
    }
}