package com.techshowcase.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openai")
public class OpenAiChatController {

    private final ChatClient openAiChatClient;

    public OpenAiChatController(@Qualifier("openAiChatClient") ChatClient openAiChatClient) {
        this.openAiChatClient = openAiChatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") final String message) {
        return openAiChatClient.prompt(message).call().content();
    }
}
