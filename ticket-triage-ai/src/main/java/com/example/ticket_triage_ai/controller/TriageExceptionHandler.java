package com.example.ticket_triage_ai.controller;

import com.example.ticket_triage_ai.service.TriageParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TriageExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getField() + ": " + e.getDefaultMessage())
				.collect(Collectors.joining("; "));
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("error", "Validation failed", "message", message));
	}

	@ExceptionHandler(TriageParseException.class)
	public ResponseEntity<Map<String, String>> handleParseError(TriageParseException ex) {
		return ResponseEntity
				.status(HttpStatus.BAD_GATEWAY)
				.body(Map.of("error", "Triage failed", "message", "LLM response could not be parsed. Please try again."));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleLlmOrOther(Exception ex) {
		String msg = ex.getMessage();
		Throwable cause = ex.getCause();
		while (cause != null) {
			if (cause.getMessage() != null) msg = cause.getMessage();
			cause = cause.getCause();
		}
		// Return 429 when Gemini quota is exceeded so clients see a clear reason
		if (msg != null && (msg.contains("429") || msg.contains("quota") || msg.contains("Quota exceeded"))) {
			return ResponseEntity
					.status(HttpStatus.TOO_MANY_REQUESTS)
					.body(Map.of("error", "Quota exceeded", "message", "Gemini API quota exceeded. Try again later or use a different API key/model."));
		}
		return ResponseEntity
				.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(Map.of("error", "Service unavailable", "message", "Triage service is temporarily unavailable. Please try again later."));
	}
}
