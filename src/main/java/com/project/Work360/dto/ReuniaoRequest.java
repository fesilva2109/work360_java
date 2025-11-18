package com.project.Work360.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReuniaoRequest (
        @NotNull(message = "O ID do usuário é obrigatório.")
        Long usuarioId,

        @NotBlank(message = "O título é obrigatório.")
        String titulo,
        String descricao,

        @NotNull(message = "A data e hora são obrigatórias.")
        @Future(message = "A data deve ser futura.")
        LocalDateTime data,
        String link
){
}
