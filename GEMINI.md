# Ticket Triage AI - Project Context

1. **Plan order:** Follow `ticket-triage-ai/TICKET_TRIAGE_PLAN.txt` for the order of work. Implement steps in sequence (1.2 -> 1.3 -> 1.4 -> 1.5, then Phase 2+).

2. **Stack:** Spring Boot 3, Java 17, Spring AI with Gemini (`spring-ai-starter-model-google-genai`). Use existing config in `application.properties`; no need to switch to YAML unless desired.

3. **API contract:**
    - Request DTO: `title`, `description` (strings).
    - Response DTO: `category`, `priority` (High/Medium/Low), `draftReply`.
    - Keep prompts and DTOs aligned with the plan.

4. **Conventions:**
    - Prefer `application.properties`.
    - Keep prompts in code or in a template.
    - Use Bean Validation for request validation.
    - Handle LLM failures with 502/503 and clear messages.

