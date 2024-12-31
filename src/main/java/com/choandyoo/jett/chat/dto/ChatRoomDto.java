package com.choandyoo.jett.chat.dto;

import com.choandyoo.jett.chat.entity.ChatRoom;
import lombok.Data;
import lombok.Getter;
import net.minidev.json.JSONArray;

import java.time.LocalDateTime;

@Data
@Getter
public class ChatRoomDto {
    private long userId;
    private String member;
    private String roomName;  // 채팅방 이름

    public ChatRoom toCreateChatRoom() {
        return ChatRoom.builder()
                .userId(userId)
                .member(member)
                .roomName(roomName)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
