**Structured Output Converter (Spring AI)**
- Purpose: Converts an LLM's plain text response into structured Java objects (POJOs), Map, List, or JSON.
- Why it's needed: Applications work better with structured data than free-form text because it's easier to parse, validate, and process.
- Before calling the LLM: Automatically adds format instructions (or a JSON schema) to the prompt so the model knows the expected output format.
- After receiving the response: Parses the raw LLM output and converts it into the requested Java type.

- Built-in converters:
    - `BeanOutputConverter` → Java POJO/Record
    - `MapOutputConverter` → Map<String, Object>
    - `ListOutputConverter` → List<T>

Below is the log message when you trigger the api ```/chat-bean?message=give 10 major citiies of the netherlands```.
Note that from entity class (CountryCities), LLM understand that the response should be given back in json format
which should adhere to the structure of CountryCities.

```aiignore
2026-07-26T23:38:04.251+02:00 DEBUG 165977 --- [10-springai-structured-output] [nio-8080-exec-2] o.s.a.c.c.advisor.SimpleLoggerAdvisor    : request: ChatClientRequest[prompt=Prompt{messages=[UserMessage{content='give 10 major citiies of the netherlands', metadata={messageType=USER}, messageType=USER}], modelOptions=org.springframework.ai.openai.OpenAiChatOptions@b48967f2}, context={spring.ai.chat.client.output.format=Your response should be in JSON format.
Do not include any explanations, only provide a RFC8259 compliant JSON response following this format without deviation.
Do not include markdown code blocks in your response.
Remove the ```json markdown from the output.
Here is the JSON Schema instance your output must adhere to:
```{
  "$schema" : "https://json-schema.org/draft/2020-12/schema",
  "type" : "object",
  "properties" : {
    "cities" : {
      "type" : "array",
      "items" : {
        "type" : "string"
      }
    },
    "country" : {
      "type" : "string"
    }
  },
  "required" : [ "cities", "country" ],
  "additionalProperties" : false
}```
}]
```

In-built implementation of `StructuredOutputConverter` can be used depending on the need of response format. 

Below log message explains the use of `ListOutputConverter`. ```/chat-list?message=give 10 major citiies of the netherlands```:
```aiignore
2026-07-26T23:55:31.534+02:00 DEBUG 168706 --- [10-springai-structured-output] [nio-8080-exec-2] o.s.a.c.c.advisor.SimpleLoggerAdvisor    : request: ChatClientRequest[prompt=Prompt{messages=[UserMessage{content='give 10 major citiies of the netherlands', metadata={messageType=USER}, messageType=USER}], modelOptions=org.springframework.ai.openai.OpenAiChatOptions@b48967f2}, context={spring.ai.chat.client.output.format=Respond with only a list of comma-separated values, without any leading or trailing text.
Example format: foo, bar, baz
}]
```

Below log message explains the use of `MapOutputConverter`. ```/chat-map?message=give short details about 10 major citiies of the netherlands```:
```aiignore
2026-07-27T00:14:33.173+02:00 DEBUG 173366 --- [10-springai-structured-output] [nio-8080-exec-2] o.s.a.c.c.advisor.SimpleLoggerAdvisor    : request: ChatClientRequest[prompt=Prompt{messages=[UserMessage{content='give very short details of 10 major citiies of the netherlands', metadata={messageType=USER}, messageType=USER}], modelOptions=org.springframework.ai.openai.OpenAiChatOptions@b48967f2}, context={spring.ai.chat.client.output.format=Your response should be in JSON format.
The data structure for the JSON should match this Java class: java.util.HashMap
Do not include any explanations, only provide a RFC8259 compliant JSON response following this format without deviation.
Remove the ```json markdown surrounding the output including the trailing "```".
}]
```