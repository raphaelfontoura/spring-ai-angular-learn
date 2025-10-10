package com.github.raphaelfontoura.api_ai.memory;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.stereotype.Service;

@Service
public class MemoryChatService {

    private final ChatClient chatClient;

    private final MemoryChatRepository memoryChatRepository;

    private static final String USER_ID = "default-user";
    private static final String PROMPT_DESCRIPTION = "Generate a concise description limit to a maximum of 30 characters for the following conversation to be used as a chat title.\n";

    public MemoryChatService(ChatClient.Builder chatClientBuilder,
            JdbcChatMemoryRepository jdbcChatMemoryRepository,
            MemoryChatRepository memoryChatRepository) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(10)
                .build();
        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor())
                .build();
        this.memoryChatRepository = memoryChatRepository;
    }

    public String simpleChat(String message) {
        return this.chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    public String chat(String message, String chatId) {
        var response = this.chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .user(message)
                .call()
                .content();
        return filterThinkBlockSubstring(response);
    }

    public NewChatResponse createNewChat(String message) {
        String description = this.generateDescription(message);
        String chatId = this.memoryChatRepository.generateChatId(USER_ID, description);
        String response = this.chat(message, chatId);
        return new NewChatResponse(chatId, description, filterThinkBlockSubstring(response));
    }

    public List<Chat> getAllChatsFromUser(String userId) {
        if (userId == null || userId.isBlank()) {
            userId = USER_ID;
        }
        return this.memoryChatRepository.getAllChatsFromUser(userId);
    }

    public List<ChatMessage> getAllMessagesFromChat(String chatId) {
        if (!this.memoryChatRepository.chatExists(chatId)) {
            throw new IllegalArgumentException("Chat ID does not exist: " + chatId);
        }
        return this.memoryChatRepository.getAllMessagesFromChat(chatId);
    }

    private String generateDescription(String message) {
        var result = this.chatClient.prompt()
                .user(PROMPT_DESCRIPTION + message)
                .call()
                .content();
        return filterThinkBlockSubstring(result);
    }

    private String filterThinkBlockSubstring(String text) {
        if (text == null) {
            return "";
        }
        text = text.replace("\n", "");
        if (text.contains("</think>")) {
            return text.substring(text.indexOf("</think>") + 8).trim();
        }
        return text.trim();
    }

}
