package com.techshowcase.springai06.default_message.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatControllerWithoutDefault {

    private final ChatClient chatClient;

    public ChatControllerWithoutDefault(final ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Cons: If we have multiple apis then we need to duplicate the same system message code in
     * each api method. To avoid duplicate code we can use default methods provided by spring-ai.
     * <p>
     * Refer {@link ChatControllerWithDefault} controller on how to use default methods.
     *
     * @param userQuery
     * @return
     */
    @GetMapping("/chat")
    public String chat(@RequestParam("userQuery") final String userQuery) {

        final String systemMessageStr = """
                You are a sports expert. You will answer the question related to sports only.
                If a user query is not related to sport then let them know that you can assist
                only with sports related queries.
                """;

        // OPTION 1: Using system() and user() methods.
        /*return chatClient
                .prompt()
                .system(systemMessageStr)
                .user(userMessage)
                .call()
                .content();*/

        // OPTION 2: Using Prompt object
        final SystemMessage systemMessage = new SystemMessage(systemMessageStr);
        final UserMessage userMessage = new UserMessage(userQuery);
        final Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        return chatClient.prompt(prompt)
                .call()
                .content();
    }

}
