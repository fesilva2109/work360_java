package com.project.Work360.dto;

import com.project.Work360.model.Prioridade;
import java.time.LocalDateTime;

public record TarefaResponse(
        Long id,
        String titulo,
        String descricao,
        Prioridade prioridade,
        Integer estimativaMinutos,
        Long usuarioId,
        boolean concluida,
        LocalDateTime dataConclusao
) {
}