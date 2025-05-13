package com.arep.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arep.prototype.dto.CalendarEventDTO;
import com.arep.prototype.service.ChatGptService;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {

    @Autowired
    private ChatGptService chatGptService;

    @PostMapping("/analizar")
    public String analizarAgenda(@RequestBody List<CalendarEventDTO> eventos) {
        return chatGptService.analizarAgenda(eventos);
    }
}

