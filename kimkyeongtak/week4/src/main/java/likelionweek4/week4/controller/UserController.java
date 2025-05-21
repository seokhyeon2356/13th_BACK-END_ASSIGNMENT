package likelionweek4.week4.controller;

import jakarta.persistence.EntityNotFoundException;
import likelionweek4.week4.dto.UserCreationRequestDto;
import likelionweek4.week4.dto.UserResponseDto;
import likelionweek4.week4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    // POST /api/users : 사용자 생성
//    @PostMapping
//    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreationRequestDto userDto) {
//        try {
//            UserResponseDto createdUser = userService.createUser(userDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }

    // GET /api/users : 전체 사용자 목록 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET /api/users/{userId} : 특정 사용자 ID로 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        try {
            UserResponseDto user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/users/{userId} : 특정 사용자 ID로 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreationRequestDto userDto) {
        try {
            UserResponseDto createdUser = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}