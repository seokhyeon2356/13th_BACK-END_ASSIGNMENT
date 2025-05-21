package com.acycycy.LikeLionWeek2.service;

import com.acycycy.LikeLionWeek2.dto.RegisterDto;
import com.acycycy.LikeLionWeek2.entity.UserEntity;
import com.acycycy.LikeLionWeek2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity register(RegisterDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        String encoded = passwordEncoder.encode(dto.getPassword());
        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .password(encoded)
                .build();
        return userRepository.save(user);
    }
}
