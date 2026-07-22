This application is to demonstrate how to use Advisors.

Advisor behaves similar to interceptor. It helps in dealing with cross-cutting concerns.
It allows:
- pre-processing or post-processing of prompt data.
- injecting additional behavior without modifying the core logic.
- chaining of multiple behaviors.

Spring AI provides number of inbuilt advisors, such as SimpleLoggerAdvisor or SafeGuardAdvisor.
An advisor implements CallAdvisor, StreamAdvisor. We can create a custom advisor and that should
implement CallAdvisor and StreamAdvisor.

SafeGuardAdvisor blocks a request if the prompt contains any of the pre-defined list of sensitive words.