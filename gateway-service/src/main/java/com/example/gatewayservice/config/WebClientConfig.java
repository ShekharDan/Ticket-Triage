package com.example.gatewayservice.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

	private static final int CONNECT_TIMEOUT_MS = 5000;
	// LLM calls can be slow; 90s avoids ReadTimeoutException when AI service waits on Gemini
	private static final int READ_TIMEOUT_SEC = 90;
	private static final int WRITE_TIMEOUT_SEC = 10;

	@Value("${ai-service.url}")
	private String aiServiceUrl;

	@Bean
	public WebClient aiServiceWebClient(WebClient.Builder builder) {
		HttpClient httpClient = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MS)
				.responseTimeout(Duration.ofSeconds(READ_TIMEOUT_SEC))
				.doOnConnected(conn -> conn
						.addHandlerLast(new ReadTimeoutHandler(READ_TIMEOUT_SEC, TimeUnit.SECONDS))
						.addHandlerLast(new WriteTimeoutHandler(WRITE_TIMEOUT_SEC, TimeUnit.SECONDS)));
		return builder
				.baseUrl(aiServiceUrl)
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}
