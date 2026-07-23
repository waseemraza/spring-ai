package com.techshowcase.springai06.default_message.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailControllerWithPromptStuffing {

    private final ChatClient chatClient;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    private Resource systemPromptTemplate;

    public EmailControllerWithPromptStuffing(final ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/email/with-prompt-stuffing")
    public String getEmailResponse(@RequestParam("userPrompt") final String userPrompt) {
        return chatClient
                .prompt()
                .system(systemPromptTemplate)
                .user(userPrompt)
                .call()
                .content();
    }
}
