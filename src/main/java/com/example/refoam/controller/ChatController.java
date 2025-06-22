package com.example.refoam.controller;

import com.example.refoam.domain.ChatMessage;
import com.example.refoam.domain.ChatRoom;
import com.example.refoam.domain.Employee;
import com.example.refoam.service.ChatRoomService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private ChatRoomService chatRoomService;
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat")
    public ChatMessage sendMessage(ChatMessage message) {
        // 메시지 저장 로직 (예: DB에 저장) 후 반환
        return message;
    }

    @GetMapping("/chat/room/{targetId}")
    public String enterChatRoom(HttpSession session,
                                @PathVariable Long targetUserId,
                                Model model) {
        Employee loginUser = (Employee) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long currentUserId = loginUser.getId();
        ChatRoom room = chatRoomService.getOrCreateRoom(currentUserId, targetUserId);

        model.addAttribute("roomId", room.getId());
        model.addAttribute("targetUserId", targetUserId);
        model.addAttribute("myId", currentUserId);
        return "chat/modal :: chatModal"; // 모달의 Thymeleaf fragment 반환
    }

}
