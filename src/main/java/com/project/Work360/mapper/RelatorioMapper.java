package com.project.Work360.mapper;


import com.project.Work360.dto.RelatorioResponse;
import com.project.Work360.model.Relatorio;

public class RelatorioMapper {

    public RelatorioResponse toResponse(Relatorio relatorio) {
        return new RelatorioResponse(
                relatorio.getId(),
                relatorio.getUsuario().getId(),
                relatorio.getDataInicio(),
                relatorio.getDataFim(),
                relatorio.getTarefasConcluidas(),
                relatorio.getTarefasPendentes(),
                relatorio.getReunioesRealizadas(),
                relatorio.getMinutosFocoTotal(),
                relatorio.getPercentualConclusao(),
                relatorio.getRiscoBurnout(),
                relatorio.getTendenciaProdutividade(),
                relatorio.getTendenciaFoco(),
                relatorio.getInsights(),
                relatorio.getRecomendacaoIA(),
                relatorio.getResumoGeral(),
                relatorio.getCriadoEm()
        );
    }
}
