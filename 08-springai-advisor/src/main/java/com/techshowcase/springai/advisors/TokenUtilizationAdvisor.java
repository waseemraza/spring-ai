package com.techshowcase.springai.advisors;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

/**
 * This is an example of how to create a custom advisor.
 */
public class TokenUtilizationAdvisor implements CallAdvisor {

    private final Logger logger = LoggerFactory.getLogger(TokenUtilizationAdvisor.class);

    @Override
    public @NonNull ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        final ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        final ChatResponse chatResponse = chatClientResponse.chatResponse();

        if (chatResponse != null) {
            final Usage usage = chatResponse.getMetadata().getUsage();
            logger.info("Token usage detail: {}", usage.toString());
        }
        return chatClientResponse;
    }

    @Override
    public @NonNull String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
