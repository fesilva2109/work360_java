package com.project.Work360.dto;

import com.project.Work360.model.Prioridade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TarefaRequest(
        @NotNull(message = "O ID do usuário é obrigatório.")
        Long usuarioId,
        @NotBlank(message = "O título é obrigatório.")
        String titulo,
        String descricao,
        @NotNull(message = "A prioridade é obrigatória.")
        Prioridade prioridade,
        @Positive(message = "A estimativa deve ser positiva.")
        Integer estimativaMinutos,
        Boolean concluida 
){
}