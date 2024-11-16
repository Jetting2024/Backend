package com.choandyoo.jett.chat.dto;

import com.choandyoo.jett.chat.entity.ChatMessage;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class ChatMessageDto {
    private long roomId;
    private long userId;
    private String message;

    public ChatMessage toSaveChatMessage() {
        return ChatMessage.builder()
                .roomId(roomId)
                .userId(userId)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
