package com.choandyoo.jett.chat.repository;

import com.choandyoo.jett.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomId(Long roomId);
}
