package com.choandyoo.jett.chat.controller;

import com.choandyoo.jett.chat.dto.ChatMessageDto;
import com.choandyoo.jett.chat.dto.ChatRoomDto;
import com.choandyoo.jett.chat.service.ChatService;
import com.choandyoo.jett.common.CustomApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    // 채팅방 생성
    @PostMapping("/createRoom")
    public ResponseEntity<CustomApiResponse<Long>> createChatRoom(@RequestBody ChatRoomDto chatRoomDto) {
        long roomId = chatService.createRoom(chatRoomDto);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(roomId));
    }

    // 메시지 전송
    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload ChatMessageDto chatMessageDto) {
        chatService.saveMessage(chatMessageDto);
        template.convertAndSend("/sub/chat/room/"+chatMessageDto.getRoomId(), chatMessageDto.getMessage());
    }

}
