package com.example.refoam.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MessageRead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private ChatMessage message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Employee employee;

    private LocalDateTime readAt;
}
