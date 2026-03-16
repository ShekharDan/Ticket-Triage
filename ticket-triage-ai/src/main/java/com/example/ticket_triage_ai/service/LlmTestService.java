package com.example.ticket_triage_ai.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class LlmTestService {

	private final ChatModel chatModel;

	public LlmTestService(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	public String sendOneMessage(String userMessage) {
		Prompt prompt = new Prompt(userMessage);
		return chatModel.call(prompt).getResult().getOutput().getText();
	}
}
