package com.github.raphaelfontoura.api_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.raphaelfontoura.api_ai.memory.ChatMessage;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(@NotNull ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping
    ChatMessage generation(@RequestBody ChatMessage message) {
        var response = this.chatClient.prompt()
                .user(message.content())
                .call()
                .content();
        return new ChatMessage(response, "ASSISTANT");
    }

}
