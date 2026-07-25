package com.techshowcase.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
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

        final OpenAiChatOptions.Builder options = OpenAiChatOptions.builder()
                .model("gpt-5.4-mini")
                .temperature(0.8)
                .maxCompletionTokens(100); // maxCompletionTokens might restrict the response to generate when the response exceeds the value. You can test with much lower value.

        return chatClientBuilder
                .defaultOptions(options)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor()))
                .defaultSystem(systemMessage)
                .build();
    }
}
