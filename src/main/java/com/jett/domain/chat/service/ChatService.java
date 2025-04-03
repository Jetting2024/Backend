package com.jett.domain.chat.service;

import com.jett.domain.chat.dto.ChatMessageDto;
import com.jett.domain.chat.dto.ChatRoomDto;
import com.jett.domain.chat.dto.ChatRoomInfoDto;

public interface ChatService {

  long createRoom(Long userId, ChatRoomDto chatRoomDto);

  void addChatroomMember(Long userId, Long roomId);

  void saveMessage(ChatMessageDto chatMessageDto);

  ChatRoomInfoDto getChatroom(Long chatroomId);

}
