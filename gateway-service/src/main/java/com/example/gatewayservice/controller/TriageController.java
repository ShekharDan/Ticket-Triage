package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.TicketRequest;
import com.example.gatewayservice.dto.TriageResult;
import com.example.gatewayservice.client.AiServiceClient;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TriageController {

	private final AiServiceClient aiServiceClient;

	public TriageController(AiServiceClient aiServiceClient) {
		this.aiServiceClient = aiServiceClient;
	}

	@PostMapping("/triage")
	public ResponseEntity<TriageResult> triage(@Valid @RequestBody TicketRequest request) {
		TriageResult result = aiServiceClient.triage(request);
		return ResponseEntity.ok(result);
	}
}
