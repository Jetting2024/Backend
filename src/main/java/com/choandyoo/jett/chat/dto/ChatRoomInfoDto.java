package com.choandyoo.jett.chat.dto;

import com.choandyoo.jett.member.dto.MemberDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatRoomInfoDto {
    private long roomId;
    private String roomName;
    private List<MemberDto> members;
}
