package likelionweek4.week4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDetailsResponseDto {

    private Long roomId;
    private String roomName;
    private List<ChatMessageResponseDto> messages;
}
