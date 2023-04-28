package com.example.messenger.repo;

import com.example.messenger.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByTag(String tag);
}
