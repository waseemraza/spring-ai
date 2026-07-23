This application demonstrates how to use ***Advisors***.

An advisor works like an interceptor and helps handle cross-cutting concerns. It allows you to:
- pre-process or post-process prompt data.
- inject additional behavior without changing the core logic.
- chain multiple behaviors together.

Spring AI ships with several built-in advisors, such as `SimpleLoggerAdvisor` and `SafeGuardAdvisor`.
Every advisor implements `CallAdvisor` and `StreamAdvisor`, so a custom advisor must implement both.

`SafeGuardAdvisor` blocks a request when the prompt contains any word from a predefined list of sensitive words.