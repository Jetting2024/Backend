package com.jett.domain.chat.dto;

import com.jett.domain.member.dto.MemberDto;
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
