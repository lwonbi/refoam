package com.example.refoam.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatMessage_id")
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime sendAt;
    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Employee sender;

    // 읽음 상태 리스트 (그룹채팅일 경우 여러 개 가능)
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<MessageRead> reads;
}