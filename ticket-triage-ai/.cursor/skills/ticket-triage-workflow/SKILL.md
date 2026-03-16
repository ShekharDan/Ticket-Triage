---
name: ticket-triage-workflow
description: Continue the ticket-triage project by implementing the next unchecked step from TICKET_TRIAGE_PLAN.txt. Use when the user asks to continue the ticket-triage project, implement the next step, or work through the plan.
---

# Ticket Triage Workflow

When the user asks to continue the ticket-triage project or implement the next step:

1. **Open the plan:** Read [TICKET_TRIAGE_PLAN.txt](ticket-triage-ai/TICKET_TRIAGE_PLAN.txt) (at project root).

2. **Find the first unchecked step:** Scan phases in order (Phase 1 → 2 → 3 → 4). Within each phase, find the first item marked `[ ]` (unchecked).

3. **Implement that step:** Follow the plan’s instructions for that step. Respect the project rule in `.cursor/rules/ticket-triage-context.mdc` (stack: Spring Boot 3, Java 17, Spring AI with Gemini; DTOs and API as in the plan).

4. **Optionally update the plan:** After implementing, you may change the step’s `[ ]` to `[x]` in TICKET_TRIAGE_PLAN.txt so progress is visible.

## Order of work

- Phase 1: Single service (LLM call, DTOs, prompt, REST, validation).
- Phase 2: Split into gateway + AI service (microservices).
- Phase 3: Docker (Dockerfiles, docker-compose).
- Phase 4: Polish (README, cleanup, GitHub).

Do not skip steps; implement in sequence unless the user asks otherwise.
