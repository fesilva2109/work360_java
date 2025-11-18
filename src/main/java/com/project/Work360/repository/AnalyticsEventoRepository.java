package com.project.Work360.repository;

import com.project.Work360.model.AnalyticsEvento;
import com.project.Work360.model.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsEventoRepository extends JpaRepository<AnalyticsEvento, Long> {

    List<AnalyticsEvento> findByUsuarioId(Long usuarioId);

    List<AnalyticsEvento> findByUsuarioIdAndTimestampBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fim);

    List<AnalyticsEvento> findByUsuarioIdAndTipoEvento(Long usuarioId, TipoEvento tipoEvento);
    
}
