package com.project.Work360.repository;

import com.project.Work360.model.Prioridade;
import com.project.Work360.model.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    List<Tarefa> findByUsuarioIdAndPrioridade(Long usuarioId, Prioridade prioridade);
    
    Page<Tarefa> findByUsuarioId(Long usuarioId, Pageable pageable);

    // Adicione este método para buscar todas as tarefas de um usuário (sem paginação)
    List<Tarefa> findAllByUsuarioId(Long usuarioId);

    long countByUsuarioIdAndDataConclusaoBetween(Long usuarioId, LocalDateTime start, LocalDateTime end);
}
