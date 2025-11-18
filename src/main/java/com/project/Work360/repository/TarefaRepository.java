package com.project.Work360.repository;

import com.project.Work360.model.Prioridade;
import com.project.Work360.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByUsuarioId(Long usuarioId);

    List<Tarefa> findByUsuarioIdAndPrioridade(Long usuarioId, Prioridade prioridade);
}
