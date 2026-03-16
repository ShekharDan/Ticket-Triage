package com.example.gatewayservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GatewayExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getField() + ": " + e.getDefaultMessage())
				.collect(Collectors.joining("; "));
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("error", "Validation failed", "message", message));
	}

	@ExceptionHandler(WebClientResponseException.ServiceUnavailable.class)
	public ResponseEntity<Map<String, String>> handle503(WebClientResponseException ex) {
		return ResponseEntity
				.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(Map.of("error", "AI service unavailable", "message", "Triage service is temporarily unavailable. Please try again later."));
	}

	@ExceptionHandler(WebClientResponseException.GatewayTimeout.class)
	public ResponseEntity<Map<String, String>> handle504(WebClientResponseException ex) {
		return ResponseEntity
				.status(HttpStatus.GATEWAY_TIMEOUT)
				.body(Map.of("error", "AI service timeout", "message", "Triage request timed out. Please try again."));
	}

	@ExceptionHandler(WebClientResponseException.class)
	public ResponseEntity<Map<String, String>> handle5xx(WebClientResponseException ex) {
		HttpStatus status = ex.getStatusCode().is5xxServerError()
				? HttpStatus.BAD_GATEWAY
				: HttpStatus.valueOf(ex.getStatusCode().value());
		return ResponseEntity
				.status(status)
				.body(Map.of("error", "AI service error", "message", "Triage service returned an error. Please try again later."));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleOther(Exception ex) {
		return ResponseEntity
				.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(Map.of("error", "Service unavailable", "message", "Unable to reach triage service. Please try again later."));
	}
}
