package com.project.Work360.service;

import com.project.Work360.dto.ReuniaoRequest;
import com.project.Work360.dto.ReuniaoResponse;
import com.project.Work360.mapper.ReuniaoMapper;
import com.project.Work360.model.Reuniao;
import com.project.Work360.model.Usuario;
import com.project.Work360.repository.ReuniaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ReuniaoService {

    private final ReuniaoRepository reuniaoRepository;
    private final UsuarioService usuarioService;
    private final ReuniaoMapper reuniaoMapper = new ReuniaoMapper();

    @Autowired
    public ReuniaoService(ReuniaoRepository reuniaoRepository, UsuarioService usuarioService) {
        this.reuniaoRepository = reuniaoRepository;
        this.usuarioService = usuarioService;
    }

    public ReuniaoResponse save(ReuniaoRequest request) {
        Usuario usuario = usuarioService.findUsuarioById(request.usuarioId());
        Reuniao reuniao = reuniaoMapper.toEntity(request, usuario);
        Reuniao salva = reuniaoRepository.save(reuniao);
        return reuniaoMapper.toResponse(salva);
    }

    public Page<ReuniaoResponse> findAll(Pageable pageable) {
        return reuniaoRepository.findAll(pageable).map(reuniaoMapper::toResponse);
    }

    public ReuniaoResponse findById(Long id) {
        return reuniaoRepository.findById(id).map(reuniaoMapper::toResponse).orElse(null);
    }

    public Reuniao findReuniaoById(Long id) {
        return reuniaoRepository.findById(id).orElse(null);
    }


    public ReuniaoResponse update(Long id, ReuniaoRequest request) {
        Optional<Reuniao> optional = reuniaoRepository.findById(id);
        if (optional.isPresent()) {
            Reuniao reuniao = optional.get();
            reuniao.setTitulo(request.titulo());
            reuniao.setDescricao(request.descricao());
            reuniao.setData(request.data());
            reuniao.setLink(request.link());
            Reuniao atualizada = reuniaoRepository.save(reuniao);
            return reuniaoMapper.toResponse(atualizada);
        }
        return null;
    }

    public boolean delete(Long id) {
        Optional<Reuniao> reuniao = reuniaoRepository.findById(id);
        if (reuniao.isPresent()) {
            reuniaoRepository.delete(reuniao.get());
            return true;
        }
        return false;
    }
}