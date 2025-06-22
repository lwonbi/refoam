package com.example.refoam.repository;

import com.example.refoam.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    @Query("""
        SELECT r FROM ChatRoom r
        JOIN r.members p1
        JOIN r.members p2
        WHERE p1.employee = :user1Id AND p2.employee = :user2Id
    """)
    Optional<ChatRoom> findByUsers(@Param("user1Id") Long user1Id,
                                   @Param("user2Id") Long user2Id);
}
