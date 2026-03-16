package com.example.ticket_triage_ai.service;

import com.example.ticket_triage_ai.dto.TicketRequest;
import com.example.ticket_triage_ai.dto.TriageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class TriageService {

	private static final String TRIAGE_SYSTEM =
			"You are a support ticket triage assistant. Given a ticket title and description, respond with ONLY a single JSON object (no markdown, no code block) with exactly these keys: "
			+ "- \"category\": one word (e.g. Billing, Technical, Access). "
			+ "- \"priority\": one of High, Medium, Low. "
			+ "- \"draftReply\": a short draft reply to the customer (one or two sentences).";

	private final ChatModel chatModel;
	private final ObjectMapper objectMapper;

	public TriageService(ChatModel chatModel, ObjectMapper objectMapper) {
		this.chatModel = chatModel;
		this.objectMapper = objectMapper;
	}

	public TriageResult triage(TicketRequest request) {
		String userPrompt = "Ticket title: " + request.getTitle() + "\nTicket description: " + request.getDescription();
		String fullPrompt = TRIAGE_SYSTEM + "\n\n" + userPrompt;
		Prompt prompt = new Prompt(fullPrompt);
		String raw = chatModel.call(prompt).getResult().getOutput().getText();
		return parseResponse(raw);
	}

	private TriageResult parseResponse(String raw) {
		String json = raw.trim();
		if (json.startsWith("```")) {
			int start = json.indexOf('{');
			int end = json.lastIndexOf('}') + 1;
			if (start >= 0 && end > start) {
				json = json.substring(start, end);
			}
		}
		try {
			return objectMapper.readValue(json, TriageResult.class);
		} catch (JsonProcessingException e) {
			throw new TriageParseException("LLM response was not valid JSON: " + raw, e);
		}
	}
}
