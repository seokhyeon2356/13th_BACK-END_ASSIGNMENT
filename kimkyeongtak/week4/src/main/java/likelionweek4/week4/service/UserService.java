package likelionweek4.week4.service;

import jakarta.persistence.EntityNotFoundException;
import likelionweek4.week4.dto.UserCreationRequestDto;
import likelionweek4.week4.dto.UserResponseDto;
import likelionweek4.week4.entity.UserEntity;
import likelionweek4.week4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 새로운 사용자 생성
//    public UserResponseDto createUser(UserCreationRequestDto userDto) {
//        UserEntity newUser = new UserEntity(userDto.getUsername());
//        UserEntity savedUser = userRepository.save(newUser);
//
//        return new UserResponseDto(savedUser.getId(), savedUser.getUsername());
//    }

    // 전체 사용자 목록 조회
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정 (*성능 최적화)
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponseDto(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    // 특정 사용자 ID로 조회
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 찾지 못함 :" + userId));

        return new UserResponseDto(user.getId(), user.getUsername()); // DTO로 변환하여 반환
    }

    // 특정 사용자 ID로 삭제
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId); // ID로 삭제
    }

    public UserResponseDto createUser(UserCreationRequestDto userDto) {
        UserEntity newUser = new UserEntity(userDto.getUsername(), userDto.getPassword());
        UserEntity savedUser = userRepository.save(newUser);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername());
    }
}
