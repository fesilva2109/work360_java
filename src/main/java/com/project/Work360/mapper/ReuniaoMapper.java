package com.project.Work360.mapper;


import com.project.Work360.dto.ReuniaoRequest;
import com.project.Work360.dto.ReuniaoResponse;
import com.project.Work360.model.Reuniao;
import com.project.Work360.model.Usuario;

public class ReuniaoMapper {

    public Reuniao toEntity(ReuniaoRequest dto, Usuario usuario) {
        Reuniao reuniao = new Reuniao();
        reuniao.setUsuario(usuario);
        reuniao.setTitulo(dto.titulo());
        reuniao.setDescricao(dto.descricao());
        reuniao.setData(dto.data());
        reuniao.setLink(dto.link());
        return reuniao;
    }

    public ReuniaoResponse toResponse(Reuniao reuniao) {
        return new ReuniaoResponse(
                reuniao.getId(),
                reuniao.getTitulo(),
                reuniao.getDescricao(),
                reuniao.getData(),
                reuniao.getLink(),
                reuniao.getUsuario().getId()
        );
    }
}
