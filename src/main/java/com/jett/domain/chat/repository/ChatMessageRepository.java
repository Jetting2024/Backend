package com.jett.domain.chat.repository;

import com.jett.domain.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomId(Long roomId);
}
