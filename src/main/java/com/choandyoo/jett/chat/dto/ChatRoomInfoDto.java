package com.choandyoo.jett.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomInfoDto {
    private long roomId;
    private long userId;
    private String member;
    private String roomName;
}
