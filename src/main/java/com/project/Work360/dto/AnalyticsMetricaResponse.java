package com.project.Work360.dto;

import java.time.LocalDate;

public record AnalyticsMetricaResponse(
        Long id,
        Long usuarioId,
        LocalDate data,
        Integer minutosFoco,
        Integer minutosReuniao,
        Integer tarefasConcluidasNoDia,
        String periodoMaisProdutivo
) {
}
