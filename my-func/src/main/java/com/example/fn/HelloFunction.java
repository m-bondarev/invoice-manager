package com.example.fn;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HelloFunction {

	public String handleRequest(String input) throws IOException, InterruptedException {
		final var objectMapper = new ObjectMapper();
		final var jsonNode = objectMapper.readTree(input);

		final var eventTime = jsonNode.at("/eventTime").textValue();
		final var eventId = jsonNode.at("/eventID").textValue();
		final var requestBody = """
				{
					"eventTime": "%s",
					"eventId": "%s"
				}""".formatted(eventTime, eventId);

		System.out.println("Request body to audit +++++ " + requestBody);

		final var httpClient = HttpClient.newHttpClient();

		final var request = HttpRequest.newBuilder()
				.uri(URI.create("http://130.61.75.135:8080/v1/audit"))
				.version(HttpClient.Version.HTTP_1_1)
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
	}

}
