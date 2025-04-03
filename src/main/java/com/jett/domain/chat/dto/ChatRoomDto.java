package com.jett.domain.chat.dto;

import com.jett.domain.chat.entity.ChatRoom;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class ChatRoomDto {
    private Long travelId;
    private String roomName;  // 채팅방 이름

    public ChatRoom toCreateChatRoom() {
        return ChatRoom.builder()
                .roomId(travelId)
                .roomName(roomName)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
