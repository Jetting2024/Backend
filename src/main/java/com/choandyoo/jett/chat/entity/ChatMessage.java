package com.choandyoo.jett.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "chat")
public class ChatMessage {
    @Id
    private Long roomId;
    private Long userId;
    private String message;
    private LocalDateTime createdAt;
}
