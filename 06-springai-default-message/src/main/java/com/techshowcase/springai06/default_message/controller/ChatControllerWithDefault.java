package com.techshowcase.springai06.default_message.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatControllerWithDefault {

    private final ChatClient chatClient;

    public ChatControllerWithDefault(final ChatClient.Builder chatClientBuilder) {
        final String systemMessage = """
                You are a sports expert. You will answer the question related to sports only.
                If a user query is not related to sports then let them know that you can assist
                only with sports related queries.
                """;

        this.chatClient = chatClientBuilder
                .defaultSystem(systemMessage)
                .build();
    }

    @GetMapping("/chat/with-default")
    public String chatWithDefault(@RequestParam("userPrompt") final String userPrompt) {
        return chatClient
                .prompt()
                .user(userPrompt)
                .call()
                .content();
    }

    /**
     * This method demonstrate how to override default message of the system.
     * @param userPrompt
     * @return
     */
    @GetMapping("/chat/default-override")
    public String chatDefaultOverride(@RequestParam("userPrompt") final String userPrompt) {
        return chatClient
                .prompt()
                .system("""
                        You are a cricket sport critic. You can assist users with cricket
                        related queries only. If you get query not related to cricket then
                        let the user know that they should ask only cricket related questions only.
                       """)
                .user(userPrompt)
                .call()
                .content();
    }
}
