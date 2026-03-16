package com.example.gatewayservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TriageResult {

	private String category;
	private String priority;

	@JsonProperty("draftReply")
	private String draftReply;

	public TriageResult() {
	}

	public TriageResult(String category, String priority, String draftReply) {
		this.category = category;
		this.priority = priority;
		this.draftReply = draftReply;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDraftReply() {
		return draftReply;
	}

	public void setDraftReply(String draftReply) {
		this.draftReply = draftReply;
	}
}
