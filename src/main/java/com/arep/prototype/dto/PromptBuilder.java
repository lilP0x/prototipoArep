package com.arep.prototype.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PromptBuilder {

    public static String buildProductivityPrompt(List<CalendarEventDTO> events) {
        StringBuilder prompt = new StringBuilder("Tengo estos eventos de calendario para la semana:\n");

        Map<LocalDate, List<CalendarEventDTO>> grouped = events.stream()
                .collect(Collectors.groupingBy(CalendarEventDTO::getDate));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        grouped.keySet().stream().sorted().forEach(date -> {
            prompt.append("- ").append(date.getDayOfWeek()).append(" ").append(date).append(":\n");
            for (CalendarEventDTO e : grouped.get(date)) {
                String horario;
                if (e.isAllDay()) {
                    horario = "Todo el día";
                } else {
                    horario = e.getStartTime().format(timeFormatter) + " a " + e.getEndTime().format(timeFormatter);
                }
                prompt.append("  • ").append(e.getTitle())
                      .append(" de ").append(horario).append("\n");
            }
        });

        prompt.append("\nPor favor, analiza estos datos y dime:\n")
              .append("1. ¿Cuánto tiempo tengo libre por día?\n")
              .append("2. ¿En qué actividades gasto más tiempo?\n")
              .append("3. ¿Qué cambios me recomiendas para mejorar mi productividad?\n");

        return prompt.toString();
    }
}
