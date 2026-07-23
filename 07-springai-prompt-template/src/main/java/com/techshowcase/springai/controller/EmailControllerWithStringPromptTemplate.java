package com.techshowcase.springai06.default_message.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailControllerWithStringPromptTemplate {

    private final ChatClient chatClient;

    private static final String promptTemplateStr = """
            A customer named {customerName} sent the following message: "{customerMessage}"
            Write a polite and helpful email response addressing the issue.
            Maintain a professional tone and provide reassurance.
            Respond as if you're writing the email body only. Don't include subject and signature.
            """;

    public EmailControllerWithStringPromptTemplate(final ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/email/with-template-string")
    public String getEmailResponse(
            @RequestParam("customerName") final String customerName,
            @RequestParam("customerMessage") final String customerMessage) {
        return chatClient
                .prompt()
                .system("""
                        You are a professional customer service assistant who helps drafting email 
                        responses to customer to improve the productivity of the customer support team.
                        """)
                .user(promptUserSpec ->
                        promptUserSpec.text(promptTemplateStr)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage)
                )
                .call()
                .content();
    }
}
