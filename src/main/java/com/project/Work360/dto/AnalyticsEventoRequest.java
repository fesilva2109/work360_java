package com.project.Work360.dto;

import com.project.Work360.model.TipoEvento;
import jakarta.validation.constraints.NotNull;

public record AnalyticsEventoRequest (
        @NotNull(message = "O ID do usuário é obrigatório.")
        Long usuarioId,
        Long tarefaId,
        Long reuniaoId,

        @NotNull(message = "O tipo do evento é obrigatório.")
        TipoEvento tipoEvento
){


}
