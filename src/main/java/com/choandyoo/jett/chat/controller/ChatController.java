package com.choandyoo.jett.chat.controller;

import com.choandyoo.jett.chat.dto.ChatMessageDto;
import com.choandyoo.jett.chat.dto.ChatRoomDto;
import com.choandyoo.jett.chat.dto.ChatRoomInfoDto;
import com.choandyoo.jett.chat.service.ChatService;
import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.config.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @Operation(summary = "채팅방 생성", description = "채팅방 생성하기")
    @PostMapping("/chat/createRoom")
    public ResponseEntity<CustomApiResponse<Long>> createChatRoom(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ChatRoomDto chatRoomDto) {
        Long userId = customUserDetails.getId();
        chatRoomDto.setUserId(userId);
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
    @GetMapping("/chat/info/{chatroomId}")
    public ResponseEntity<CustomApiResponse<ChatRoomInfoDto>> getChatroom(@PathVariable("chatroomId") Long chatroomId) {
        ChatRoomInfoDto chatRoomInfoDto = chatService.getChatroom(chatroomId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(chatRoomInfoDto));
    }

    @Operation(summary = "채팅 내용 불러오기", description = "특정 채팅방에서 채팅 내용 불러오기")
    @GetMapping("/chat/{chatroomId}/getMessages")
    public ResponseEntity<CustomApiResponse<List<ChatMessageDto>>> getMessage(@PathVariable("chatroomId") Long chatroomId) {
        List<ChatMessageDto> messages = chatService.getMessages(chatroomId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(messages));
    }
}
