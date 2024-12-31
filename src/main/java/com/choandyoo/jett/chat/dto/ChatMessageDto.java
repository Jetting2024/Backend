package com.choandyoo.jett.chat.dto;

import com.choandyoo.jett.chat.entity.ChatMessage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class ChatMessageDto {
    private long roomId;
    private long userId;
    private String message;
    private LocalDateTime createdAt;

    public ChatMessage toSaveChatMessage() {
        return ChatMessage.builder()
                .roomId(roomId)
                .userId(userId)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ChatMessageDto fromEntity(ChatMessage entity) {
        return ChatMessageDto.builder()
                .roomId(entity.getRoomId())
                .userId(entity.getUserId())
                .message(entity.getMessage())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
