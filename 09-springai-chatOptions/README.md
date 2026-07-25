This application demonstrates how to use ***Chat Options***.

***Chat Options*** are the runtime parameters that tune how a model generates its response. `ChatOptions` (provider-agnostic) is 
an interface in Spring AI. If we add openai related dependency then we get `OpenAiChatOptions` as its implementation. 

ChatOptions can be set: 
- globally in `application.yaml` (`spring.ai.openai.chat.options.*`) 
- as defaults on the `ChatClient`
- per-request via `.options(...)` on the prompt — the per-request values override the defaults.

#### Common options

| Option | Description |
| --- | --- |
| `model` | The model to use for the request (e.g. `gpt-5-nano`, `ai/smollm2`). |
| `temperature` | Controls randomness; higher (e.g. `0.9`) is more creative, lower (e.g. `0.2`) is more focused and deterministic. |
| `topP` | Nucleus sampling; the model considers only the smallest set of tokens whose cumulative probability reaches `topP`. An alternative to `temperature`. |
| `topK` | Limits sampling to the `K` most likely next tokens (not supported by every provider). |
| `maxTokens` | The maximum number of tokens to generate in the response. |
| `frequencyPenalty` | Penalizes tokens by how often they have already appeared, discouraging verbatim repetition. Range `-2.0` to `2.0`. |
| `presencePenalty` | Penalizes tokens that have appeared at all, encouraging the model to introduce new topics. Range `-2.0` to `2.0`. |
| `stopSequences` | A list of strings that, when generated, cause the model to stop producing further tokens. |
| `n` | How many completion choices to generate for a single prompt. |
| `seed` | A value that makes sampling more reproducible across identical requests (provider-dependent). |

#### Setting options per request

```java
chatClient.prompt()
        .user(message)
        .options(OpenAiChatOptions.builder()
                .model("gpt-5-nano")
                .temperature(0.7)
                .maxTokens(500)
                .build())
        .call()
        .content();
```

###### Example:
```localhost:8080/chat?message=Write a short poem about the sea```
