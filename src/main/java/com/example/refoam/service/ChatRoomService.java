package com.example.refoam.service;

import com.example.refoam.domain.ChatRoom;
import com.example.refoam.domain.ChatRoomUser;
import com.example.refoam.domain.Employee;
import com.example.refoam.repository.ChatRoomRepository;
import com.example.refoam.repository.ChatRoomUserRepository;
import com.example.refoam.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final EmployeeRepository employeeRepository;

    public ChatRoom getOrCreateRoom(Long currentUserId, Long targetUserId){
        // 사용자 둘이 참여 중인 기존 채팅방 조회
        return chatRoomRepository.findByUsers(currentUserId, targetUserId)
                .orElseGet(() -> {
                    ChatRoom room = new ChatRoom();
                    room.setCreatedAt(LocalDateTime.now());
                    chatRoomRepository.save(room);
                    Employee currentUser = employeeRepository.findById(currentUserId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
                    Employee targetUser = employeeRepository.findById(targetUserId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
                    chatRoomUserRepository.save(new ChatRoomUser(room, currentUser));
                    chatRoomUserRepository.save(new ChatRoomUser(room, targetUser));
                    return room;
                });
    }
}
