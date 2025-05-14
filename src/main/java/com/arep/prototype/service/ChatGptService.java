package com.arep.prototype.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arep.prototype.dto.CalendarEventDTO;
import com.arep.prototype.dto.ChatRequest;
import com.arep.prototype.dto.ChatResponse;
import com.arep.prototype.dto.Message;
import com.arep.prototype.dto.PromptBuilder;

import java.util.List;

@Service
public class ChatGptService {

    private final String KEY = ""; // Reemplaza con tu clave
    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String analizarAgenda(List<CalendarEventDTO> eventos) {
        String prompt = PromptBuilder.buildProductivityPrompt(eventos);

        RestTemplate restTemplate = new RestTemplate();
        Message userMessage = new Message("user", prompt);
        ChatRequest request = new ChatRequest("gpt-3.5-turbo", List.of(userMessage));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(KEY);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<ChatResponse> response = restTemplate.postForEntity(API_URL, entity, ChatResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getChoices().get(0).getMessage().getContent();
        } else {
            throw new RuntimeException("Error al obtener respuesta de OpenAI: " + response.getStatusCode());
        }
    }
}

