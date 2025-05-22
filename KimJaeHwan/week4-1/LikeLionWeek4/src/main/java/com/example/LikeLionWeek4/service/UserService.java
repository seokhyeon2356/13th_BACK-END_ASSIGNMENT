package com.example.LikeLionWeek4.service;

import com.example.LikeLionWeek4.dto.ChatRoomResponseDto;
import com.example.LikeLionWeek4.dto.UserCreationRequestDto;
import com.example.LikeLionWeek4.dto.UserResponseDto;
import com.example.LikeLionWeek4.entity.UserChatRoomEntity;
import com.example.LikeLionWeek4.entity.UserEntity;
import com.example.LikeLionWeek4.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //페스워드 추가해 create
    public UserResponseDto createUser(UserCreationRequestDto userDto){
        UserEntity newUser = new UserEntity(userDto.getUsername(), userDto.getPassword());
        UserEntity savedUser = userRepository.save(newUser);
        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getPassword());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getPassword())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자 찾지 못함 :" + userId));
        return new UserResponseDto(user.getId(), user.getUsername(), user.getPassword());
    }
    //username으로 모든 채팅방 return하기
    public Set<ChatRoomResponseDto> getAllRooms(String username){
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("사용자 찾지 못함 :" + username));
        Set<UserChatRoomEntity> chatrooms = user.getUserChatRooms();
        return chatrooms.stream().map(chatroom -> new ChatRoomResponseDto(chatroom.getChatRoom().getId(), chatroom.getChatRoom().getRoomName())).collect(Collectors.toSet());
    }
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
