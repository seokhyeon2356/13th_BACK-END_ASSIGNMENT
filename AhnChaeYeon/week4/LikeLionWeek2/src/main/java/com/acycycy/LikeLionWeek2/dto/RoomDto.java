package com.acycycy.LikeLionWeek2.dto;

import com.acycycy.LikeLionWeek2.entity.RoomEntity;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private String ownerUsername;

    public static RoomDto fromEntity(RoomEntity e) {
        return new RoomDto(
                e.getId(),
                e.getName(),
                e.getOwner() != null ? e.getOwner().getUsername() : null
        );
    }
}
