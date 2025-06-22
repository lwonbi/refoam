package com.example.refoam.repository;

import com.example.refoam.domain.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser,Long> {
}
