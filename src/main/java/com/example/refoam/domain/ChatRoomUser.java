package com.example.refoam.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChatRoomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoomUser_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Employee employee;
    private LocalDateTime joinedAt;
    public ChatRoomUser(ChatRoom chatRoom, Employee user) {
        this.chatRoom = chatRoom;
        this.employee = user;
        this.joinedAt = LocalDateTime.now();
    }
}
