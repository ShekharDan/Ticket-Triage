package com.example.ticket_triage_ai.controller;

import com.example.ticket_triage_ai.dto.TicketRequest;
import com.example.ticket_triage_ai.dto.TriageResult;
import com.example.ticket_triage_ai.service.LlmTestService;
import com.example.ticket_triage_ai.service.TriageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TriageController {

	private final LlmTestService llmTestService;
	private final TriageService triageService;

	public TriageController(LlmTestService llmTestService, TriageService triageService) {
		this.llmTestService = llmTestService;
		this.triageService = triageService;
	}

	@GetMapping("/test")
	public ResponseEntity<String> testLlm(@RequestParam(defaultValue = "Say hello in one sentence.") String message) {
		String response = llmTestService.sendOneMessage(message);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/triage")
	public ResponseEntity<TriageResult> triage(@Valid @RequestBody TicketRequest request) {
		TriageResult result = triageService.triage(request);
		return ResponseEntity.ok(result);
	}
}
