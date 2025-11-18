package com.project.Work360.dto;

import com.project.Work360.model.TipoEvento;

import java.time.LocalDateTime;

public record AnalyticsEventoResponse(
        Long id,
        Long usuarioId,
        Long tarefaId,
        Long reuniaoId,
        TipoEvento tipoEvento,
        LocalDateTime timestamp
) {
}
