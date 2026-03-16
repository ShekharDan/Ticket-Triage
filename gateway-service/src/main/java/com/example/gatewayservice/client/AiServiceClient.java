package com.example.gatewayservice.client;

import com.example.gatewayservice.dto.TicketRequest;
import com.example.gatewayservice.dto.TriageResult;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AiServiceClient {

	private final WebClient webClient;

	public AiServiceClient(WebClient aiServiceWebClient) {
		this.webClient = aiServiceWebClient;
	}

	public TriageResult triage(TicketRequest request) {
		return webClient
				.post()
				.uri("/triage")
				.bodyValue(request)
				.retrieve()
				.bodyToMono(TriageResult.class)
				.block();
	}
}
