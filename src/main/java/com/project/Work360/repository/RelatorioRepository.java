package com.project.Work360.repository;

import com.project.Work360.model.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

    List<Relatorio> findByUsuarioId(Long usuarioId);

    Relatorio findByUsuarioIdAndDataInicio(Long usuarioId, LocalDate dataInicio);

    List<Relatorio> findByDataInicioBetween(LocalDate inicio, LocalDate fim);
}

