Providing LLM with short or long context before answering the user's question is called ***Prompt Stuffing***.

Prompt stuffing can be used when the system message is not too long. If the system context needs
to be large number of pages then follow another approach because of the following concerns:
- An LLM has limitations on the number of tokens as input.
- You need to pay money for the number of tokens passing as input.

If the context is huge then RAG is used.