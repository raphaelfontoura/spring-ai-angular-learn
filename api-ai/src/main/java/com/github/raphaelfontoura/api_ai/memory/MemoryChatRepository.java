package com.github.raphaelfontoura.api_ai.memory;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryChatRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemoryChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String generateChatId(String userId, String description) {
        String sql = "INSERT INTO CHAT_MEMORY (user_id, description) VALUES (?, ?) RETURNING conversation_id";
        return this.jdbcTemplate.queryForObject(sql, String.class, userId, description);
    }

    public List<Chat> getAllChatsFromUser(String userId) {
        String sql = "SELECT conversation_id, user_id, description FROM CHAT_MEMORY WHERE user_id = ?";
        return this.jdbcTemplate.query(sql,
                (rs, rowNum) -> new Chat(rs.getString("conversation_id"), rs.getString("description")), userId);
    }

    public List<ChatMessage> getAllMessagesFromChat(String chatId) {
        String sql = "SELECT content, type FROM SPRING_AI_CHAT_MEMORY WHERE conversation_id = ? ORDER BY timestamp ASC";
        return this.jdbcTemplate.query(sql,
                (rs, rowNum) -> new ChatMessage(rs.getString("content"), rs.getString("type")), chatId);
    }

    public boolean chatExists(String chatId) {
        String sql = "SELECT COUNT(*) FROM CHAT_MEMORY WHERE conversation_id = ?::uuid";
        Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, chatId);
        return count != null && count > 0;
    }

}
