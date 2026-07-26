This application is to demonstrate how to call openapi model using springai.
`spring-ai-starter-model-openai` library is used to interact with openapi.
Refer `application.yaml` to find out the required configuration.

#### Reading the response

`chatClient.prompt(...).call()` returns a `CallResponseSpec`. It offers several methods to read the
model's answer — pick the one that matches how much of the response you need.

| Method | Description                                                                                                                                   |
| --- |-----------------------------------------------------------------------------------------------------------------------------------------------|
| `content()` | Returns just the assistant message text as a `String`. The simplest option when only the answer matters.                                      |
| `chatResponse()` | Returns the full `ChatResponse` — all `Generation` results plus `ChatResponseMetadata` (model, id, token `Usage`, rate limits, finish reason). |
| `chatClientResponse()` | Returns the `ChatClientResponse`, which wraps the `ChatResponse` together with `context` map.                                                 |
| `entity(Class<T>)` | Maps the response directly into your own POJO. Spring AI asks the model for JSON and converts it for you.         |
| `entity(ParameterizedTypeReference<T>)` | Same as above, for generic types such as `List<Movie>` where the type parameter must be preserved.                                            |
| `responseEntity(Class<T>)` | Returns a `ResponseEntity<ChatResponse, T>` — the mapped entity *and* the raw `ChatResponse`, when you need both.                             |

###### Example:
```java
// plain text
String text = chatClient.prompt("Tell me a joke").call().content();

// full response + metadata
ChatResponse response = chatClient.prompt("Tell me a joke").call().chatResponse();
Usage usage = response.getMetadata().getUsage();

// structured output
record Joke(String setup, String punchline) {}
Joke joke = chatClient.prompt("Tell me a joke").call().entity(Joke.class);
```

