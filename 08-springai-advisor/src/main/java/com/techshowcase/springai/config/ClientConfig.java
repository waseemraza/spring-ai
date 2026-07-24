package com.techshowcase.springai.config;

import com.techshowcase.springai.advisors.TokenUtilizationAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ClientConfig {

    @Bean
    public ChatClient chatClient(final ChatClient.Builder chatClientBuilder) {
        final String systemMessage = """
                You are a sports expert. You will answer the question related to sports only.
                If a user query is not related to sports then let them know that you can assist
                only with sports related queries.
                """;

        // SimpleLoggerAdvisor - A spring provided simple logger advisor that logs the request and response messages.
        // SimpleLoggerAdvisor logs the request and response only in DEBUG mode.
        //
        // TokenUtilizationAdvisor - It's a custom advisor that gives the detail about token usage.
        return chatClientBuilder
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUtilizationAdvisor()))
                .defaultSystem(systemMessage)
                .build();
    }
}
