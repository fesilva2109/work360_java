package com.project.Work360.oracle.repository;

import com.project.Work360.oracle.model.FocusSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {
    List<FocusSession> findByUsuarioId(Long usuarioId);
}