package com.github.raphaelfontoura.api_ai.memory;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/chat-memory")
public class MemoryChatController {

    private final MemoryChatService memoryChatService;

    public MemoryChatController(MemoryChatService memoryChatService) {
        this.memoryChatService = memoryChatService;
    }

    @PostMapping("/{chatId}")
    public ChatMessage chat(@PathVariable String chatId, @RequestBody ChatMessage chatMessage) {
        var response = memoryChatService.chat(chatMessage.content(), chatId);
        return new ChatMessage(response, "ASSISTANT");
    }

    @PostMapping("/start")
    public NewChatResponse startNewChat(@RequestBody ChatMessage chatMessage) {
        return memoryChatService.createNewChat(chatMessage.content());
    }

    @GetMapping
    public List<Chat> getAllChatsFromUser(@RequestParam(required = false) String userId) {
        return memoryChatService.getAllChatsFromUser(userId);
    }

    @GetMapping("/{chatId}")
    public List<ChatMessage> getAllMessagesFromChat(@PathVariable String chatId) {
        return memoryChatService.getAllMessagesFromChat(chatId);
    }
    
    
}
