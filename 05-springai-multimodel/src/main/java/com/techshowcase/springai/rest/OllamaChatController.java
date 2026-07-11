package com.techshowcase.springai.rest;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ollama")
public class OllamaChatController {

    private final ChatClient ollamaChatClient;

    public OllamaChatController(@Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }
    
    @GetMapping("/chat")
    public String chat(@RequestParam("message") final String message) {
        return ollamaChatClient.prompt(message).call().content();
    }
}
