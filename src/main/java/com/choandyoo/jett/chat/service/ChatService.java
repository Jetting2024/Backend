package com.choandyoo.jett.chat.service;

import com.choandyoo.jett.chat.dto.ChatMessageDto;
import com.choandyoo.jett.chat.dto.ChatRoomDto;
import com.choandyoo.jett.chat.dto.ChatRoomInfoDto;
import com.choandyoo.jett.chat.entity.ChatMessage;
import com.choandyoo.jett.chat.entity.ChatRoom;
import com.choandyoo.jett.chat.entity.ChatRoomMember;
import com.choandyoo.jett.chat.repository.ChatMessageRepository;
import com.choandyoo.jett.chat.repository.ChatRepository;
import com.choandyoo.jett.chat.repository.ChatRoomMemberRepository;
import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public long createRoom(Long userId, ChatRoomDto chatRoomDto) {
        ChatRoom savedRoom = chatRepository.save(chatRoomDto.toCreateChatRoom());
        addChatroomMember(userId, savedRoom.getRoomId());
        return savedRoom.getRoomId();
    }

    @Transactional
    public void addChatroomMember(long userId, Long roomId) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("no user"));
        ChatRoom chatRoom = chatRepository.findById(roomId).orElseThrow(() -> new RuntimeException("no chatroom"));
        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build();
        chatRoomMemberRepository.save(chatRoomMember);
    }

    @Transactional
    public void saveMessage(ChatMessageDto chatMessageDto) {
        ChatMessage savedChatMessage = chatMessageDto.toSaveChatMessage();
        chatMessageRepository.save(savedChatMessage);
    }

//    @Transactional
//    public ChatRoomInfoDto getChatroom(Long chatroomId) {
//        ChatRoom chatRoom = chatRepository.findById(chatroomId).orElseThrow(() -> new RuntimeException("no chatRoom"));
//        ChatRoomInfoDto chatRoomInfoDto = ChatRoomInfoDto.builder()
//                .roomId(chatRoom.getRoomId())
//                .userId(chatRoom.getUserId())
//                .member(chatRoom.getMember())
//                .roomName(chatRoom.getRoomName())
//                .build();
//        return chatRoomInfoDto;
//    }

    @Transactional
    public List<ChatMessageDto> getMessages(Long roomId) {
        return chatMessageRepository.findAllByRoomId(roomId)
                .stream()
                .map(ChatMessageDto::fromEntity)
                .collect(Collectors.toList());
    }
}
