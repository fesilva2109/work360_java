package com.project.Work360.mapper;


import com.project.Work360.dto.AnalyticsMetricaResponse;
import com.project.Work360.model.AnalyticsMetrica;

public class AnalyticsMetricaMapper {

    public AnalyticsMetricaResponse toResponse(AnalyticsMetrica metrica) {
        return new AnalyticsMetricaResponse(
                metrica.getId(),
                metrica.getUsuario().getId(),
                metrica.getData(),
                metrica.getMinutosFoco(),
                metrica.getMinutosReuniao(),
                metrica.getTarefasConcluidasNoDia(),
                metrica.getPeriodoMaisProdutivo()
        );
    }
}
