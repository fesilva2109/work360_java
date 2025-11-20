package com.project.Work360.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Work360.dto.TarefaRequest;
import com.project.Work360.dto.TarefaResponse;
import com.project.Work360.mapper.TarefaMapper;
import com.project.Work360.model.Tarefa;
import com.project.Work360.model.Usuario;
import com.project.Work360.repository.TarefaRepository; 
import java.time.LocalDateTime;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioService usuarioService;
    private final TarefaMapper tarefaMapper = new TarefaMapper();

    @Autowired
    public TarefaService(TarefaRepository tarefaRepository, UsuarioService usuarioService) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public TarefaResponse save(TarefaRequest request) {
        Usuario usuario = usuarioService.findUsuarioById(request.usuarioId());
        Tarefa tarefa = tarefaMapper.toEntity(request, usuario);
        // The 'concluida' field in the entity defaults to false, which is correct for creation.
        Tarefa salva = tarefaRepository.save(tarefa);
        return tarefaMapper.toResponse(salva);
    }

    @Transactional(readOnly = true)
    public Page<TarefaResponse> findAllByUsuario(Long usuarioId, Pageable pageable) {
        Page<Tarefa> tarefas = tarefaRepository.findByUsuarioId(usuarioId, pageable);
        return tarefas.map(tarefaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<TarefaResponse> findAllAdmin(Pageable pageable) { // Renomeado para clareza
        return tarefaRepository.findAll(pageable).map(tarefaMapper::toResponse); // Este método busca TUDO, ideal para um admin.
    }

    @Transactional(readOnly = true)
    public TarefaResponse findById(Long id) {
        return tarefaRepository.findById(id).map(tarefaMapper::toResponse).orElse(null);
    }

    public Tarefa findTarefaById(Long id) {
        return tarefaRepository.findById(id).orElse(null);
    }

    @Transactional 
    public TarefaResponse update(Long id, TarefaRequest request) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));

        tarefa.setTitulo(request.titulo());
        tarefa.setDescricao(request.descricao());
        tarefa.setPrioridade(request.prioridade());
        tarefa.setEstimativaMinutos(request.estimativaMinutos());

        if (request.concluida() != null) {
            if (request.concluida() && !tarefa.isConcluida()) {
                tarefa.setDataConclusao(LocalDateTime.now());
            } else if (!request.concluida()) {
                tarefa.setDataConclusao(null);
            }
            tarefa.setConcluida(request.concluida());
        }

        Tarefa atualizada = tarefaRepository.save(tarefa);
        return tarefaMapper.toResponse(atualizada);
    }

    @Transactional
    public boolean delete(Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
