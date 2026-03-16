package com.example.gatewayservice.dto;

import jakarta.validation.constraints.NotBlank;

public class TicketRequest {

	@NotBlank(message = "title is required")
	private String title;

	@NotBlank(message = "description is required")
	private String description;

	public TicketRequest() {
	}

	public TicketRequest(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
