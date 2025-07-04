package com.example.week4.Service;

import com.example.week4.Dto.ChatRoomResponseDto;
import com.example.week4.Dto.UserChatRoomDto;
import com.example.week4.Dto.UserCreationRequestDto;
import com.example.week4.Dto.UserResponseDto;
import com.example.week4.Entity.UserEntity;
import com.example.week4.Repository.UserChatRoomRepository;
import com.example.week4.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    public UserService(UserRepository userRepository, UserChatRoomRepository userChatRoomRepository) {
        this.userRepository = userRepository;
        this.userChatRoomRepository = userChatRoomRepository;
    }

    public UserResponseDto createUser(UserCreationRequestDto userDto) {
        UserEntity newUser = new UserEntity(userDto.getUsername(), userDto.getPassword());
        UserEntity savedUser = userRepository.save(newUser);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(user-> new UserResponseDto(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId).
                orElseThrow(() -> new EntityNotFoundException("사용자를 찾지 못함 : " + userId));

        return new UserResponseDto(user.getId(), user.getUsername());   // Dto로 변환하여 반환
    }

    // user 지우기
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserChatRoomDto> getChatRoom(Long userId) {
        return userChatRoomRepository.findUserChatRoomByUserId(userId);
    }
}
