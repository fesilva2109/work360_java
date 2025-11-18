package com.project.Work360.dto;

import java.time.LocalDateTime;

public record ReuniaoResponse(
        Long id,
        String titulo,
        String descricao,
        LocalDateTime data,
        String link,
        Long usuarioId
) {
}