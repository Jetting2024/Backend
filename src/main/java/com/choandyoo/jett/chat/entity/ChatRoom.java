package com.choandyoo.jett.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONArray;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;
    @Column
    private long userId;
    @Column
    private String member;
    @Column
    private String roomName;
    @Column
    private LocalDateTime createdAt;
}
