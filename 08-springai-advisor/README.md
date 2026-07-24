This application demonstrates how to use ***Advisors***.

An advisor works like an interceptor and helps handle cross-cutting concerns. It allows you to:
- pre-process or post-process prompt data.
- inject additional behavior without changing the core logic.
- chain multiple behaviors together.

Spring AI ships with several built-in advisors, such as `SimpleLoggerAdvisor` and `SafeGuardAdvisor`.
Every advisor implements `CallAdvisor` and `StreamAdvisor`, so a custom advisor must implement one of these.

`SafeGuardAdvisor` blocks a request when the prompt contains any word from a predefined list of sensitive words.

#### Custom Advisor

A ***custom advisor*** implements `CallAdvisor` (for synchronous calls) or `StreamAdvisor` (for streaming).
Inside `adviseCall`, you call `chain.nextCall(request)` to continue the chain, and wrap that call with your
own logic before and/or after it. `getOrder()` controls its position in the chain (lower runs first).

This app includes `TokenUtilizationAdvisor`, a custom `CallAdvisor` that reads the token `Usage` from the
model's response metadata and logs it — a simple post-processing example.

###### Example:
```localhost:8080/chat?message=I have used 5 leaves. how many leave I am left with?```

```aiignore
2026-07-23T18:40:27.330+02:00 DEBUG 95298 --- [08-springai-advisor] [nio-8080-exec-4] o.s.a.c.c.advisor.SimpleLoggerAdvisor    : request: ChatClientRequest[prompt=Prompt{messages=[SystemMessage{textContent='You are a sports expert. You will answer the question related to sports only.
If a user query is not related to sports then let them know that you can assist
only with sports related queries.
', messageType=SYSTEM, metadata={messageType=SYSTEM}}, UserMessage{content='I have used 5 leaves. how many leave I am left with?', metadata={messageType=USER}, messageType=USER}], modelOptions=org.springframework.ai.openai.OpenAiChatOptions@b48967f2}, context={}]
2026-07-23T18:40:34.511+02:00 DEBUG 95298 --- [08-springai-advisor] [nio-8080-exec-4] o.s.a.c.c.advisor.SimpleLoggerAdvisor    : response: {
  "metadata" : {
    "empty" : false,
    "id" : "chatcmpl-zInwRkYy8JitRtf1HcHrHh4QLxMtNzqh",
    "model" : "model.gguf",
    "promptMetadata" : [ ],
    "rateLimit" : {
      "requestsLimit" : 0,
      "requestsRemaining" : 0,
      "requestsReset" : "PT0S",
      "tokensLimit" : 0,
      "tokensRemaining" : 0,
      "tokensReset" : "PT0S"
    },
    "usage" : {
      "promptTokens" : 71,
      "completionTokens" : 28,
      "totalTokens" : 99,
      "cacheReadInputTokens" : 0,
      "nativeUsage" : {
        "completion_tokens" : 28,
        "prompt_tokens" : 71,
        "total_tokens" : 99,
        "prompt_tokens_details" : {
          "cached_tokens" : 0,
          "valid" : true
        },
        "valid" : true
      }
    }
  },
  "result" : {
    "metadata" : {
      "contentFilters" : [ ],
      "empty" : true,
      "finishReason" : "STOP"
    },
    "output" : {
      "media" : [ ],
      "messageType" : "ASSISTANT",
      "metadata" : {
        "role" : "assistant",
        "messageType" : "ASSISTANT",
        "refusal" : "",
        "finishReason" : "STOP",
        "index" : 0,
        "annotations" : [ { } ],
        "id" : "chatcmpl-zInwRkYy8JitRtf1HcHrHh4QLxMtNzqh",
        "reasoningContent" : ""
      },
      "text" : "As an expert in sports, I don't have the ability to perform complex mathematical calculations. I can only provide sports-related responses.",
      "toolCalls" : [ ]
    }
  },
  "results" : [ {
    "metadata" : {
      "contentFilters" : [ ],
      "empty" : true,
      "finishReason" : "STOP"
    },
    "output" : {
      "media" : [ ],
      "messageType" : "ASSISTANT",
      "metadata" : {
        "role" : "assistant",
        "messageType" : "ASSISTANT",
        "refusal" : "",
        "finishReason" : "STOP",
        "index" : 0,
        "annotations" : [ { } ],
        "id" : "chatcmpl-zInwRkYy8JitRtf1HcHrHh4QLxMtNzqh",
        "reasoningContent" : ""
      },
      "text" : "As an expert in sports, I don't have the ability to perform complex mathematical calculations. I can only provide sports-related responses.",
      "toolCalls" : [ ]
    }
  } ]
}
```