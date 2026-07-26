package com.techshowcase.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(final ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") final String message) {
        return chatClient.prompt(message).call().content();
    }

    /**
     * Streaming variant of {@link #chat(String)}.
     *
     * <p>{@code call()} is blocking — it waits for the model to finish generating and then returns the
     * whole answer as a {@code String}. {@code stream()} instead returns a {@code Flux<String>} that
     * emits each chunk (token) as soon as the model produces it, so the user starts reading the answer
     * while it is still being generated.
     *
     * <p>Note: this endpoint cannot be tried from Postman — it buffers the response and shows it only
     * once the stream completes, which hides the streaming effect. Open it in a browser instead
     * (e.g. {@code localhost:8080/stream?message=Tell me a story}), as the browser renders the chunks
     * as they arrive.
     *
     * @param message the user prompt
     * @return the assistant's answer, emitted chunk by chunk
     */
    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }

    /**
     * This api will return the list of 5 most popular personalities in the given sport name.
     * But if you give the invalid sport name, it will return the list of 5 most popular personalities in that
     * field.
     *
     * This is fixed in
     * @param name
     * @return
     */
    @GetMapping("/sports")
    public String getSportPersonalities(@RequestParam String name) {

        final String promptTemplate = "Give me the list of 5 most popular personalities in {name}. Show the result in readable format.";

        final PromptTemplate promptTemplateObject = new PromptTemplate(promptTemplate);
        final Prompt prompt = promptTemplateObject.create(Map.of("name", name));

        // Or you can use userMessage instead of PromptTemplate
        /* final UserMessage userMessage = new UserMessage(String.format("Give me the list of 5 most popular personalities in %s. Show the result in readable format.", name));
        final Prompt prompt = new Prompt(userMessage);*/

        return chatClient.prompt(prompt)
                .call()
                .content();
    }

    /**
     * This api will return the list of 5 most popular personalities in the given sport name.
     * If you give the invalid sport name, it will apologize and say that it can only answer sports related questions.
     * <p>
     * This method demonstrates the use of SystemMessage to set the context of the conversation.
     *
     * @param name
     * @return
     */
    @GetMapping("/sports-expert")
    public String getSportPersonalitiesOnly(@RequestParam String name) {

        final SystemMessage systemMessage = new SystemMessage("You are a sports expert. You will answer the question related to sports only.");
        final UserMessage userMessage = new UserMessage(String.format("Give me the list of 5 most popular personalities in %s. Show the result in readable format.", name));
        final Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}
