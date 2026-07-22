package com.techshowcase.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public ChatClient chatClient(final ChatClient.Builder chatClientBuilder) {
        final String systemMessage = """
                You are a sports expert. You will answer the question related to sports only.
                If a user query is not related to sports then let them know that you can assist
                only with sports related queries.
                """;

        // SimpleLoggerAdvisor - A simple logger advisor that logs the request and response messages.
        // SimpleLoggerAdvisor logs the request and response only in DEBUG mode.
        // It's also possible not to use defaultAdvisors() but with the prompt itself.
        return chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem(systemMessage)
                .build();
    }
}
