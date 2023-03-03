package com.example.startup.messages.repository;

import com.example.startup.messages.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Message, Integer> {
}
