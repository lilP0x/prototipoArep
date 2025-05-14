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

        prompt.append("\nPor favor, analiza estos datos y responde exclusivamente en el siguiente formato JSON:\n")
        .append("{\n")
        .append("  \"daily\": {\n")
        .append("    \"YYYY-MM-DD\": {\n")
        .append("      \"actividades\": {\n")
        .append("        \"reuniones\": 0.0, // porcentaje sobre 24h\n")
        .append("        \"trabajo\": 0.0,\n")
        .append("        \"ocio\": 0.0,\n")
        .append("        \"descanso\": 0.0,\n")
        .append("        \"estudio\": 0.0\n")
        .append("    \"deporte\": 0.0\n")
        .append("      },\n")
        .append("      \"recomendacion\": \"...\"\n")
        .append("    }\n")
        .append("  },\n")
        .append("  \"weekly\": {\n")
        .append("    \"reuniones\": 0.0, // porcentaje sobre 168h\n")
        .append("    \"trabajo\": 0.0,\n")
        .append("    \"ocio\": 0.0,\n")
        .append("    \"descanso\": 0.0,\n")
        .append("    \"estudio\": 0.0\n")
        .append("    \"deporte\": 0.0\n")
        .append("  }\n")
        .append("}\n")
        .append("Importante: Los valores deben estar expresados como porcentajes decimales (por ejemplo: 12.5 representa 12.5%). No uses horas. No incluyas explicaciones, texto adicional ni comentarios fuera del JSON.");


        return prompt.toString();
    }
}
