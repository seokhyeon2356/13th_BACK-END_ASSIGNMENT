package likelionweek4.week4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDetailResponseDto {

    private Long id;
    private String roomName;
    private List<ChatMessageRequestDto> messages;
}
