package com.acycycy.LikeLionWeek2.dto;

import com.acycycy.LikeLionWeek2.entity.ChatEntity;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatDto {
    private Long id;
    private String message;
    private String senderUsername;
    private String sentAt;
    private boolean read;
    private int readCount;

    public static ChatDto fromEntity(ChatEntity c, String currentUsername) {
        boolean isRead = c.getReadBy().stream()
                .anyMatch(u -> u.getUsername().equals(currentUsername));
        return ChatDto.builder()
                .id(c.getId())
                .message(c.getMessage())
                .senderUsername(c.getSender().getUsername())
                .sentAt(c.getSentAt().toString())
                .read(isRead)
                .readCount(c.getReadBy().size())
                .build();
    }
}
