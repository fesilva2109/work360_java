package com.project.Work360.mapper;


import com.project.Work360.dto.TarefaRequest;
import com.project.Work360.dto.TarefaResponse;
import com.project.Work360.model.Tarefa;
import com.project.Work360.model.Usuario;

public class TarefaMapper {

    public Tarefa toEntity(TarefaRequest dto, Usuario usuario) {
        Tarefa tarefa = new Tarefa();
        tarefa.setUsuario(usuario);
        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setPrioridade(dto.prioridade());
        tarefa.setEstimativaMinutos(dto.estimativaMinutos());

        if (dto.concluida() != null) {
            tarefa.setConcluida(dto.concluida());
        } else {
            tarefa.setConcluida(false);
        }
        
        return tarefa;
    }

    public TarefaResponse toResponse(Tarefa tarefa) {
        return new TarefaResponse(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getPrioridade(),
                tarefa.getEstimativaMinutos(),
                tarefa.getUsuario().getId(),
                tarefa.isConcluida() 
        );
    }
}
