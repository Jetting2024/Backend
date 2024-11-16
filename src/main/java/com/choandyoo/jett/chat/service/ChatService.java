package com.choandyoo.jett.chat.service;

import com.choandyoo.jett.chat.dto.ChatMessageDto;
import com.choandyoo.jett.chat.dto.ChatRoomDto;
import com.choandyoo.jett.chat.entity.ChatMessage;
import com.choandyoo.jett.chat.entity.ChatRoom;
import com.choandyoo.jett.chat.repository.ChatMessageRepository;
import com.choandyoo.jett.chat.repository.ChatRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;

    // roomName으로 채팅장 생성
    @Transactional
    public long createRoom(ChatRoomDto chatRoomDto) {
        ChatRoom savedRoom = chatRepository.save(chatRoomDto.toCreateChatRoom());
        return savedRoom.getRoomId();
    }

    @Transactional
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void saveMessage(ChatMessageDto chatMessageDto) {
        ChatMessage savedChatMessage = chatMessageDto.toSaveChatMessage();
        chatMessageRepository.save(savedChatMessage);
    }

}
