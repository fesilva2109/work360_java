package com.project.Work360.repository;

import com.project.Work360.model.Reuniao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

    List<Reuniao> findByUsuarioId(Long usuarioId);

    List<Reuniao> findByUsuarioIdAndDataBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fim);
}
