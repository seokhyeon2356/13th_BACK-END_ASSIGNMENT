package com.acycycy.LikeLionWeek2.repository;

import com.acycycy.LikeLionWeek2.entity.RoomMemberEntity;
import com.acycycy.LikeLionWeek2.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMemberEntity, Long> {

    @Query("""
        SELECT DISTINCT r
        FROM RoomMemberEntity rm
        JOIN rm.room r
        JOIN rm.user u
        WHERE u.username = :username
    """)
    List<RoomEntity> findRoomsByUsername(@Param("username") String username);
}
