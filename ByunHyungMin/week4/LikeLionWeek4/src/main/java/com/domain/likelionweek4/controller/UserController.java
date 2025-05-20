package com.domain.likelionweek4.controller;

import com.domain.likelionweek4.entity.UserEntity;
import com.domain.likelionweek4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserEntity user) {
        userRepository.save(user);
        return ResponseEntity.ok("회원가입 성공");
    }
}
