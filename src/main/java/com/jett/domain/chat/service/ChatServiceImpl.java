package com.jett.domain.chat.service;

import com.jett.domain.chat.dto.ChatMessageDto;
import com.jett.domain.chat.dto.ChatRoomDto;
import com.jett.domain.chat.dto.ChatRoomInfoDto;
import com.jett.domain.chat.entity.ChatMessage;
import com.jett.domain.chat.entity.ChatRoom;
import com.jett.domain.chat.entity.ChatRoomMember;
import com.jett.domain.chat.repository.ChatMessageRepository;
import com.jett.domain.chat.repository.ChatRepository;
import com.jett.domain.chat.repository.ChatRoomMemberRepository;
import com.jett.domain.member.dto.MemberDto;
import com.jett.domain.member.entity.Member;
import com.jett.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
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
    public void addChatroomMember(Long userId, Long roomId) {
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

    @Transactional
    public ChatRoomInfoDto getChatroom(Long chatroomId) {
        ChatRoom chatRoom = chatRepository.findById(chatroomId).orElseThrow(() -> new RuntimeException("no chatRoom"));
        List<MemberDto> memberDtos = chatRoom.getChatRoomMembers().stream()
                .map(chatRoomMember -> MemberDto.builder()
                        .id(chatRoomMember.getMember().getId())
                        .name(chatRoomMember.getMember().getName())
                        .email(chatRoomMember.getMember().getEmail())
                        .build())
                .toList();

        ChatRoomInfoDto chatRoomInfoDto = ChatRoomInfoDto.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .members(memberDtos)
                .build();
        return chatRoomInfoDto;
    }
    
    @Transactional
    public List<ChatMessageDto> getMessages(Long roomId) {
        return chatMessageRepository.findAllByRoomId(roomId)
                .stream()
                .map(ChatMessageDto::fromEntity)
                .collect(Collectors.toList());
    }

}
