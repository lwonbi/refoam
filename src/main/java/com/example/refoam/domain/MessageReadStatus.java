package com.example.refoam.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class MessageReadStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ChatMessage message;
    @ManyToOne
    private Employee employee;
    private LocalDateTime readAt;
}
