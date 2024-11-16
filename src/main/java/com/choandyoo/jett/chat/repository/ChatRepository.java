package com.choandyoo.jett.chat.repository;

import com.choandyoo.jett.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatRoom, Long> {
}
