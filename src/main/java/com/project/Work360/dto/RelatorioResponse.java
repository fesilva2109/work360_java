package com.project.Work360.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RelatorioResponse (
        Long id,
        Long usuarioId,
        LocalDate dataInicio,
        LocalDate dataFim,
        Integer tarefasConcluidas,
        Integer tarefasPendentes,
        Integer reunioesRealizadas,
        Integer minutosFocoTotal,
        Double percentualConclusao,
        Double riscoBurnout,
        String tendenciaProdutividade,
        String tendenciaFoco,
        String insights,
        String recomendacaoIA,
        String resumoGeral,
        LocalDateTime criadoEm
){
}
