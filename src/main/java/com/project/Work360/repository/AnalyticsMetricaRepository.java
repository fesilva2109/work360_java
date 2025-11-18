package com.project.Work360.repository;

import com.project.Work360.model.AnalyticsMetrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnalyticsMetricaRepository extends JpaRepository<AnalyticsMetrica, Long> {

    List<AnalyticsMetrica> findByUsuarioId(Long usuarioId);

    List<AnalyticsMetrica> findByUsuarioIdAndDataBetween(Long usuarioId, LocalDate inicio, LocalDate fim);

    void deleteByUsuarioIdAndData(Long usuarioId, LocalDate data);

    Optional<AnalyticsMetrica> findByUsuarioIdAndData(Long usuarioId, LocalDate data);

}
