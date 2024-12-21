package com.choandyoo.jett.chat.controller;

import com.choandyoo.jett.chat.dto.ChatMessageDto;
import com.choandyoo.jett.chat.dto.ChatRoomDto;
import com.choandyoo.jett.chat.dto.ChatRoomInfoDto;
import com.choandyoo.jett.chat.service.ChatService;
import com.choandyoo.jett.common.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "채팅방 생성", description = "채팅방 생성하기")
    @PostMapping("/createRoom")
    public ResponseEntity<CustomApiResponse<Long>> createChatRoom(@RequestBody ChatRoomDto chatRoomDto) {
        long roomId = chatService.createRoom(chatRoomDto);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(roomId));
    }

    @Operation(summary = "메시지 전송", description = "메시지 전송하기")
    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload ChatMessageDto chatMessageDto) {
        chatService.saveMessage(chatMessageDto);
        template.convertAndSend("/sub/chat/room/"+chatMessageDto.getRoomId(), chatMessageDto.getMessage());
    }

    @Operation(summary = "채팅방 불러오기", description = "채팅방 불러오기")
    @GetMapping("/info/{chatroomId}")
    public ResponseEntity<CustomApiResponse<ChatRoomInfoDto>> getChatroom(@PathVariable("chatroomId") Long chatroomId) {
        ChatRoomInfoDto chatRoomInfoDto = chatService.getChatroom(chatroomId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(chatRoomInfoDto));
    }
}
