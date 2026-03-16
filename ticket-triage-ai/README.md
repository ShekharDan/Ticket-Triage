# Ticket Triage AI

Support ticket triage using an LLM: send a ticket (title + description) and get **category**, **priority**, and a **draft reply**.

## Tech stack

- **Spring Boot 3** (Java 17)
- **Spring AI** (Google Gemini) for triage
- **Gateway service**: Spring Web + WebClient to call the AI service
- **Docker** + Docker Compose to run both services

## Architecture

- **AI service** (port 8081): Spring AI + Gemini; single endpoint `POST /triage`.
- **Gateway** (port 8080): Forwards `POST /api/triage` to the AI service; clients call only the gateway.

## How to run

### 1. Locally (no Docker)

**AI service**

```bash
cd ticket-triage-ai
export GEMINI_API_KEY=your-gemini-api-key   # or set in env
./mvnw spring-boot:run
```

Runs on **http://localhost:8081**.

**Gateway** (in another terminal)

```bash
cd gateway-service
./mvnw spring-boot:run
```

Runs on **http://localhost:8080**. Ensure the AI service is running first.

### 2. Docker Compose (both services)

From the **Project** directory (parent of `ticket-triage-ai` and `gateway-service`):

```bash
export GEMINI_API_KEY=your-gemini-api-key
docker-compose up --build
```

- Gateway: **http://localhost:8080**
- AI service: **http://localhost:8081** (internal)

Or from **ticket-triage-ai** (gateway built from `../gateway-service`):

```bash
export GEMINI_API_KEY=your-gemini-api-key
docker-compose up --build
```

## Example request and response

**Request** (POST to gateway or AI service):

```bash
curl -X POST http://localhost:8080/api/triage \
  -H "Content-Type: application/json" \
  -d '{"title":"Cannot login","description":"After password reset I get error 500 on login page."}'
```

**Response:**

```json
{
  "category": "Technical",
  "priority": "High",
  "draftReply": "We're sorry you're experiencing login issues after your password reset. Our team will look into the error and get back to you shortly."
}
```

## Environment

- **AI service**: `GEMINI_API_KEY` (required for real LLM calls; default `demo-key` will fail on actual triage).
- **Gateway**: `AI_SERVICE_URL` (optional; default `http://localhost:8081`; use `http://ai-service:8081` in Docker).
