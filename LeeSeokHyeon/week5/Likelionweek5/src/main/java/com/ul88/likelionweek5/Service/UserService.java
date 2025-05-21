package com.ul88.likelionweek5.Service;

import com.ul88.likelionweek5.Dto.UserCreationRequestDto;
import com.ul88.likelionweek5.Dto.UserDto;
import com.ul88.likelionweek5.Dto.UserResponseDto;
import com.ul88.likelionweek5.Entity.UserEntity;
import com.ul88.likelionweek5.Repository.UserRepository;
import com.ul88.likelionweek5.util.PasswordUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserCreationRequestDto userDto){

        UserEntity newUser = new UserEntity(UserDto.getUsername());
        UserEntity savedUser = userRepository.save(newUser);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers(){
        List<UserEntity> Users = userRepository.findAll();

        return users.stream().map(user -> new UserResponseDto(user.getId(), user.getUsername()))
                    .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId){
        UserEntity User = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 찾지 못함 : " + userId));

        return new UserResponseDto(user.getId(), user.getUsername());
    }

    public void register(String username, String rawPassword){
        UserEntity user = new UserEntity();
        user.setUsername(username);

        String encryptedPassword = PasswordUtil.encode(rawPassword);
        user.setPassword(encryptedPassword);

        userRepository.save(user);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public boolean login(String username, String rawPassword){
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        boolean matched = PasswordUtil.matches(rawPassword, user.getPassword());

        if(matched){
            System.out.println("로그인 성공!");
            return true;
        } else {
            System.out.println("비밀번호 틀림!");
            return false;
        }
    }
}
