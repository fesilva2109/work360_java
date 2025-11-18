package com.project.Work360.mapper;


import com.project.Work360.dto.AnalyticsEventoRequest;
import com.project.Work360.dto.AnalyticsEventoResponse;
import com.project.Work360.model.AnalyticsEvento;
import com.project.Work360.model.Reuniao;
import com.project.Work360.model.Tarefa;
import com.project.Work360.model.Usuario;

public class AnalyticsEventoMapper {

    public AnalyticsEvento toEntity(AnalyticsEventoRequest dto, Usuario usuario, Tarefa tarefa, Reuniao reuniao) {
        AnalyticsEvento evento = new AnalyticsEvento();
        evento.setUsuario(usuario);
        evento.setTarefa(tarefa);
        evento.setReuniao(reuniao);
        evento.setTipoEvento(dto.tipoEvento());
        return evento;
    }

    public AnalyticsEventoResponse toResponse(AnalyticsEvento evento) {
        return new AnalyticsEventoResponse(
                evento.getId(),
                evento.getUsuario().getId(),
                evento.getTarefa() != null ? evento.getTarefa().getId() : null,
                evento.getReuniao() != null ? evento.getReuniao().getId() : null,
                evento.getTipoEvento(),
                evento.getTimestamp()
        );
    }
}
