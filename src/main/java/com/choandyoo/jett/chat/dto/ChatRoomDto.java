package com.choandyoo.jett.chat.dto;

import com.choandyoo.jett.chat.entity.ChatRoom;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class ChatRoomDto {
    private long userId;
    private String roomName;  // 채팅방 이름

    public ChatRoom toCreateChatRoom() {
        return ChatRoom.builder()
                .userId(userId)
                .roomName(roomName)
                .createdAt(LocalDateTime.now())
                .build();
    }
}